package com.personalizedlearning.system.service.impl;

import com.personalizedlearning.system.entity.Grade;
import com.personalizedlearning.system.mapper.GradeMapper;
import com.personalizedlearning.system.service.RabbitMQMessageSender;
import com.personalizedlearning.system.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;
    
    @Autowired
    private RabbitMQMessageSender messageSender;

    @Override
    @Cacheable(value = "grade", key = "#id")
    public Grade findById(Long id) {
        return gradeMapper.findById(id);
    }

    @Override
    @Cacheable(value = "grades", key = "'student_' + #studentId")
    public List<Grade> findByStudentId(Long studentId) {
        return gradeMapper.findByStudentId(studentId);
    }

    @Override
    @Cacheable(value = "grades", key = "'course_' + #courseId")
    public List<Grade> findByCourseId(Long courseId) {
        return gradeMapper.findByCourseId(courseId);
    }

    @Override
    @Cacheable(value = "grades", key = "'student_' + #studentId + '_course_' + #courseId")
    public Grade findByStudentIdAndCourseId(Long studentId, Long courseId) {
        return gradeMapper.findByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    @Cacheable(value = "grades", key = "'all'")
    public List<Grade> findAll() {
        return gradeMapper.findAll();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"grades"}, key = {"'all'", "'student_' + #grade.studentId", "'course_' + #grade.courseId", "'student_' + #grade.studentId + '_course_' + #grade.courseId"}, allEntries = true)
    public int insert(Grade grade) {
        int result = gradeMapper.insert(grade);
        
        // 发送成绩更新消息
        if (result > 0) {
            Map<String, Object> message = new HashMap<>();
            message.put("gradeId", grade.getId());
            message.put("studentId", grade.getStudentId());
            message.put("courseId", grade.getCourseId());
            message.put("score", grade.getScore());
            message.put("action", "INSERT");
            message.put("timestamp", System.currentTimeMillis());
            messageSender.sendGradeUpdatedMessage(message);
        }
        
        return result;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"grade", "grades"}, key = {"#grade.id", "'student_' + #grade.studentId", "'course_' + #grade.courseId", "'student_' + #grade.studentId + '_course_' + #grade.courseId"}, allEntries = true)
    public int update(Grade grade) {
        int result = gradeMapper.update(grade);
        
        // 发送成绩更新消息
        if (result > 0) {
            Map<String, Object> message = new HashMap<>();
            message.put("gradeId", grade.getId());
            message.put("studentId", grade.getStudentId());
            message.put("courseId", grade.getCourseId());
            message.put("score", grade.getScore());
            message.put("action", "UPDATE");
            message.put("timestamp", System.currentTimeMillis());
            messageSender.sendGradeUpdatedMessage(message);
        }
        
        return result;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"grade", "grades"}, key = {"#id", "'all'"}, allEntries = true)
    public int delete(Long id) {
        return gradeMapper.delete(id);
    }

    @Override
    @Transactional
    public boolean batchInsertOrUpdate(List<Grade> grades) {
        if (grades == null || grades.isEmpty()) {
            return false;
        }
        int result = gradeMapper.batchInsertOrUpdate(grades);
        return result > 0;
    }

    @Override
    @Cacheable(value = "grades", key = "'range_' + #minScore + '_' + #maxScore")
    public List<Grade> findByScoreRange(Double minScore, Double maxScore) {
        return gradeMapper.findByScoreRange(minScore, maxScore);
    }

    @Override
    @Cacheable(value = "gradeStatistics", key = "'average_' + #courseId")
    public Double calculateAverageScoreByCourseId(Long courseId) {
        List<Grade> grades = gradeMapper.findByCourseId(courseId);
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        int count = 0;
        for (Grade grade : grades) {
            if (grade.getScore() != null) {
                sum += grade.getScore();
                count++;
            }
        }
        return count > 0 ? sum / count : 0.0;
    }

    @Override
    public Double calculateAverageScoreByStudentId(Long studentId) {
        List<Grade> grades = gradeMapper.findByStudentId(studentId);
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        int count = 0;
        for (Grade grade : grades) {
            if (grade.getScore() != null) {
                sum += grade.getScore();
                count++;
            }
        }
        return count > 0 ? sum / count : 0.0;
    }

    @Override
    @Cacheable(value = "gradeStatistics", key = "'course_' + #courseId")
    public GradeStatistics getGradeStatisticsByCourseId(Long courseId) {
        List<Grade> grades = gradeMapper.findByCourseId(courseId);
        if (grades == null || grades.isEmpty()) {
            return new GradeStatistics(0.0, 0.0, 0.0, 0, 0, 0.0);
        }

        double highestScore = Double.MIN_VALUE;
        double lowestScore = Double.MAX_VALUE;
        double sum = 0.0;
        int count = 0;
        int passedCount = 0;

        for (Grade grade : grades) {
            if (grade.getScore() != null) {
                double score = grade.getScore();
                highestScore = Math.max(highestScore, score);
                lowestScore = Math.min(lowestScore, score);
                sum += score;
                count++;
                if (score >= 60.0) {
                    passedCount++;
                }
            }
        }

        double averageScore = count > 0 ? sum / count : 0.0;
        double passRate = count > 0 ? (double) passedCount / count * 100 : 0.0;

        return new GradeStatistics(highestScore, lowestScore, averageScore, 
                                 grades.size(), passedCount, passRate);
    }
}
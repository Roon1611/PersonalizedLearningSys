package com.personalizedlearning.system.service.impl;

import com.personalizedlearning.system.entity.LearningProgress;
import com.personalizedlearning.system.mapper.LearningProgressMapper;
import com.personalizedlearning.system.service.LearningProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LearningProgressServiceImpl implements LearningProgressService {

    @Autowired
    private LearningProgressMapper progressMapper;

    @Override
    public LearningProgress findById(Long id) {
        return progressMapper.findById(id);
    }

    @Override
    public LearningProgress findByStudentIdAndCourseId(Long studentId, Long courseId) {
        return progressMapper.findByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public List<LearningProgress> findByStudentId(Long studentId) {
        return progressMapper.findByStudentId(studentId);
    }

    @Override
    public List<LearningProgress> findByCourseId(Long courseId) {
        return progressMapper.findByCourseId(courseId);
    }

    @Override
    public List<LearningProgress> findAll() {
        return progressMapper.findAll();
    }

    @Override
    @Transactional
    public int insert(LearningProgress progress) {
        return progressMapper.insert(progress);
    }

    @Override
    @Transactional
    public int update(LearningProgress progress) {
        return progressMapper.update(progress);
    }

    @Override
    @Transactional
    public int delete(Long id) {
        return progressMapper.delete(id);
    }

    @Override
    @Transactional
    public boolean batchUpdate(List<LearningProgress> progresses) {
        if (progresses == null || progresses.isEmpty()) {
            return false;
        }
        int result = progressMapper.batchUpdate(progresses);
        return result > 0;
    }

    @Override
    public List<LearningProgress> findByCompletionRate(Integer minRate, Integer maxRate) {
        return progressMapper.findByCompletionRate(minRate, maxRate);
    }

    @Override
    @Transactional
    public boolean updateCompletionRate(Long studentId, Long courseId, Integer completionRate) {
        LearningProgress progress = progressMapper.findByStudentIdAndCourseId(studentId, courseId);
        if (progress != null) {
            progress.setCompletionRate(completionRate);
            progress.setUpdatedAt(new Date());
            int result = progressMapper.update(progress);
            return result > 0;
        }
        return false;
    }

    @Override
    public Double calculateAverageCompletionRateByCourseId(Long courseId) {
        List<LearningProgress> progresses = progressMapper.findByCourseId(courseId);
        if (progresses == null || progresses.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        int count = 0;
        for (LearningProgress progress : progresses) {
            if (progress.getCompletionRate() != null) {
                sum += progress.getCompletionRate();
                count++;
            }
        }
        return count > 0 ? (double) sum / count : 0.0;
    }

    @Override
    public Double calculateAverageCompletionRateByStudentId(Long studentId) {
        List<LearningProgress> progresses = progressMapper.findByStudentId(studentId);
        if (progresses == null || progresses.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        int count = 0;
        for (LearningProgress progress : progresses) {
            if (progress.getCompletionRate() != null) {
                sum += progress.getCompletionRate();
                count++;
            }
        }
        return count > 0 ? (double) sum / count : 0.0;
    }

    @Override
    public ProgressStatistics getProgressStatisticsByCourseId(Long courseId) {
        List<LearningProgress> progresses = progressMapper.findByCourseId(courseId);
        if (progresses == null || progresses.isEmpty()) {
            return new ProgressStatistics(0.0, 0, 0, 0, 0, 0.0);
        }

        int highestRate = Integer.MIN_VALUE;
        int lowestRate = Integer.MAX_VALUE;
        int sum = 0;
        int count = 0;
        int completedCount = 0;

        for (LearningProgress progress : progresses) {
            if (progress.getCompletionRate() != null) {
                int rate = progress.getCompletionRate();
                highestRate = Math.max(highestRate, rate);
                lowestRate = Math.min(lowestRate, rate);
                sum += rate;
                count++;
                if (rate >= 100) {
                    completedCount++;
                }
            }
        }

        double averageRate = count > 0 ? (double) sum / count : 0.0;
        double completionRate = count > 0 ? (double) completedCount / count * 100 : 0.0;

        return new ProgressStatistics(averageRate, highestRate, lowestRate, 
                                    progresses.size(), completedCount, completionRate);
    }

    @Override
    public List<LearningProgress> getMockProgressData() {
        // 创建模拟数据列表
        List<LearningProgress> mockProgresses = new ArrayList<>();
        Date now = new Date();
        
        // 模拟学生1的课程进度数据
        LearningProgress progress1 = new LearningProgress();
        progress1.setId(1L);
        progress1.setStudentId(101L);
        progress1.setCourseId(1L);
        progress1.setCompletionRate(85);
        progress1.setLastAccessTime(Date.from(now.toInstant().minusSeconds(86400))); // 昨天
        progress1.setTotalStudyTime(1200); // 20小时
        progress1.setQuizScores("85,90,78");
        progress1.setCreatedAt(Date.from(now.toInstant().minusSeconds(604800))); // 一周前
        progress1.setUpdatedAt(now);
        progress1.setStudentName("张三");
        progress1.setCourseName("高等数学");
        mockProgresses.add(progress1);
        
        // 模拟学生2的课程进度数据
        LearningProgress progress2 = new LearningProgress();
        progress2.setId(2L);
        progress2.setStudentId(102L);
        progress2.setCourseId(1L);
        progress2.setCompletionRate(60);
        progress2.setLastAccessTime(Date.from(now.toInstant().minusSeconds(172800))); // 前天
        progress2.setTotalStudyTime(800); // 约13小时
        progress2.setQuizScores("65,70,62");
        progress2.setCreatedAt(Date.from(now.toInstant().minusSeconds(604800))); // 一周前
        progress2.setUpdatedAt(now);
        progress2.setStudentName("李四");
        progress2.setCourseName("高等数学");
        mockProgresses.add(progress2);
        
        // 模拟学生3的课程进度数据
        LearningProgress progress3 = new LearningProgress();
        progress3.setId(3L);
        progress3.setStudentId(103L);
        progress3.setCourseId(1L);
        progress3.setCompletionRate(95);
        progress3.setLastAccessTime(Date.from(now.toInstant().minusSeconds(3600))); // 1小时前
        progress3.setTotalStudyTime(1500); // 25小时
        progress3.setQuizScores("95,92,98");
        progress3.setCreatedAt(Date.from(now.toInstant().minusSeconds(604800))); // 一周前
        progress3.setUpdatedAt(now);
        progress3.setStudentName("王五");
        progress3.setCourseName("高等数学");
        mockProgresses.add(progress3);
        
        // 模拟学生4的课程进度数据
        LearningProgress progress4 = new LearningProgress();
        progress4.setId(4L);
        progress4.setStudentId(104L);
        progress4.setCourseId(2L);
        progress4.setCompletionRate(40);
        progress4.setLastAccessTime(Date.from(now.toInstant().minusSeconds(259200))); // 3天前
        progress4.setTotalStudyTime(500); // 约8小时
        progress4.setQuizScores("45,50,42");
        progress4.setCreatedAt(Date.from(now.toInstant().minusSeconds(432000))); // 5天前
        progress4.setUpdatedAt(now);
        progress4.setStudentName("赵六");
        progress4.setCourseName("线性代数");
        mockProgresses.add(progress4);
        
        // 模拟学生5的课程进度数据
        LearningProgress progress5 = new LearningProgress();
        progress5.setId(5L);
        progress5.setStudentId(105L);
        progress5.setCourseId(2L);
        progress5.setCompletionRate(70);
        progress5.setLastAccessTime(Date.from(now.toInstant().minusSeconds(43200))); // 12小时前
        progress5.setTotalStudyTime(900); // 15小时
        progress5.setQuizScores("75,70,68");
        progress5.setCreatedAt(Date.from(now.toInstant().minusSeconds(432000))); // 5天前
        progress5.setUpdatedAt(now);
        progress5.setStudentName("孙七");
        progress5.setCourseName("线性代数");
        mockProgresses.add(progress5);
        
        return mockProgresses;
    }
}
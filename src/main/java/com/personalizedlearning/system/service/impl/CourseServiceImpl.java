package com.personalizedlearning.system.service.impl;

import com.personalizedlearning.system.entity.Course;
import com.personalizedlearning.system.mapper.CourseMapper;
import com.personalizedlearning.system.service.RabbitMQMessageSender;
import com.personalizedlearning.system.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    
    @Autowired
    private RabbitMQMessageSender messageSender;

    @Override
    @Cacheable(value = "course", key = "#id")
    public Course findById(Long id) {
        return courseMapper.findById(id);
    }

    @Override
    @Cacheable(value = "courses", key = "'all'")
    public List<Course> findAll() {
        return courseMapper.findAll();
    }

    @Override
    @Cacheable(value = "courses", key = "'teacher_' + #teacherId")
    public List<Course> findByTeacherId(Long teacherId) {
        return courseMapper.findByTeacherId(teacherId);
    }

    @Override
    @Cacheable(value = "courses", key = "'student_' + #studentId")
    public List<Course> findByStudentId(Long studentId) {
        return courseMapper.findByStudentId(studentId);
    }

    @Override
    @CacheEvict(value = "courses", key = "'all'", allEntries = true)
    public int insert(Course course) {
        int result = courseMapper.insert(course);
        
        // 发送课程更新消息（新创建课程）
        if (result > 0) {
            Map<String, Object> message = new HashMap<>();
            message.put("courseId", course.getId());
            message.put("courseName", course.getCourseName());
            message.put("action", "CREATE");
            message.put("timestamp", System.currentTimeMillis());
            messageSender.sendCourseUpdatedMessage(message);
        }
        
        return result;
    }

    @Override
    @CacheEvict(value = {"course", "courses"}, key = "#course.id", allEntries = true)
    public int update(Course course) {
        int result = courseMapper.update(course);
        
        // 发送课程更新消息
        if (result > 0) {
            Map<String, Object> message = new HashMap<>();
            message.put("courseId", course.getId());
            message.put("courseName", course.getCourseName());
            message.put("action", "UPDATE");
            message.put("timestamp", System.currentTimeMillis());
            messageSender.sendCourseUpdatedMessage(message);
        }
        
        return result;
    }

    @Override
    @CacheEvict(value = {"course", "courses"}, key = "#id", allEntries = true)
    public int delete(Long id) {
        int result = courseMapper.delete(id);
        
        // 发送课程更新消息（删除课程）
        if (result > 0) {
            Map<String, Object> message = new HashMap<>();
            message.put("courseId", id);
            message.put("action", "DELETE");
            message.put("timestamp", System.currentTimeMillis());
            messageSender.sendCourseUpdatedMessage(message);
        }
        
        return result;
    }

    @Override
    @CacheEvict(value = {"courses"}, key = {"'all'", "'student_' + #studentId", "'teacher_' + #courseId"}, allEntries = true)
    public boolean addStudentToCourse(Long studentId, Long courseId) {
        // 先检查学生是否已经加入课程
        if (checkStudentCourseRelation(studentId, courseId)) {
            return false; // 学生已经在课程中
        }
        int result = courseMapper.addStudentToCourse(studentId, courseId);
        return result > 0;
    }

    @Override
    @CacheEvict(value = {"courses"}, key = {"'all'", "'student_' + #studentId", "'teacher_' + #courseId"}, allEntries = true)
    public boolean removeStudentFromCourse(Long studentId, Long courseId) {
        // 先检查学生是否在课程中
        if (!checkStudentCourseRelation(studentId, courseId)) {
            return false; // 学生不在课程中
        }
        int result = courseMapper.removeStudentFromCourse(studentId, courseId);
        return result > 0;
    }

    @Override
    public boolean checkStudentCourseRelation(Long studentId, Long courseId) {
        int count = courseMapper.checkStudentCourseRelation(studentId, courseId);
        return count > 0;
    }

    @Override
    public List<Course> searchCourses(String keyword) {
        // 在实际实现中，这里可能需要调用专门的搜索方法
        // 目前先返回所有课程
        return courseMapper.findAll();
    }
}
package com.personalizedlearning.system.service.impl;

import com.personalizedlearning.system.entity.Assignment;
import com.personalizedlearning.system.mapper.AssignmentMapper;
import com.personalizedlearning.system.service.RabbitMQMessageSender;
import com.personalizedlearning.system.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Override
    @Cacheable(value = "assignment", key = "#id")
    public Assignment findById(Long id) {
        return assignmentMapper.findById(id);
    }

    @Override
    @Cacheable(value = "assignments", key = "'course_' + #courseId")
    public List<Assignment> findByCourseId(Long courseId) {
        return assignmentMapper.findByCourseId(courseId);
    }

    @Override
    public List<Assignment> findPendingAssignmentsByStudentId(Long studentId) {
        return assignmentMapper.findPendingAssignmentsByStudentId(studentId);
    }

    @Override
    public List<Assignment> findSubmittedAssignmentsByStudentId(Long studentId) {
        return assignmentMapper.findSubmittedAssignmentsByStudentId(studentId);
    }

    @Override
    @Cacheable(value = "assignments", key = "'all'")
    public List<Assignment> findAll() {
        return assignmentMapper.findAll();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"assignments"}, key = {"'all'", "'course_' + #assignment.courseId"}, allEntries = true)
    public int insert(Assignment assignment) {
        int result = assignmentMapper.insert(assignment);
        
        // 发送作业创建消息
        if (result > 0) {
            Map<String, Object> message = new HashMap<>();
            message.put("assignmentId", assignment.getId());
            message.put("assignmentName", assignment.getTitle());
            message.put("courseId", assignment.getCourseId());
            message.put("teacherId", assignment.getTeacherId());
            message.put("deadline", assignment.getDeadline());
            message.put("timestamp", System.currentTimeMillis());
            messageSender.sendAssignmentCreatedMessage(message);
        }
        
        return result;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"assignment", "assignments"}, key = {"#assignment.id", "'course_' + #assignment.courseId"}, allEntries = true)
    public int update(Assignment assignment) {
        return assignmentMapper.update(assignment);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"assignment", "assignments"}, key = {"#id", "'all'"}, allEntries = true)
    public int delete(Long id) {
        return assignmentMapper.delete(id);
    }

    @Override
    public List<Assignment> findByDeadline(Date startDate, Date endDate) {
        return assignmentMapper.findByDeadline(startDate, endDate);
    }

    @Override
    public int updateStatus(Long id, String status) {
        return assignmentMapper.updateStatus(id, status);
    }

    @Override
    @Cacheable(value = "assignments", key = "'teacher_' + #teacherId")
    public List<Assignment> findByTeacherId(Long teacherId) {
        // 在实际实现中，这里可能需要根据教师ID获取其教授的课程，然后再获取课程相关的作业
        // 目前先返回所有作业
        return assignmentMapper.findAll();
    }

    @Override
    public List<Assignment> findUpcomingAssignments(int days) {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date futureDate = calendar.getTime();
        return assignmentMapper.findByDeadline(today, futureDate);
    }
}
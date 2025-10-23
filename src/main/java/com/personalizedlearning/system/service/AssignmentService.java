package com.personalizedlearning.system.service;

import com.personalizedlearning.system.entity.Assignment;

import java.util.Date;
import java.util.List;

public interface AssignmentService {
    // 根据ID查询作业
    Assignment findById(Long id);

    // 根据课程ID查询作业
    List<Assignment> findByCourseId(Long courseId);

    // 查询学生待提交作业
    List<Assignment> findPendingAssignmentsByStudentId(Long studentId);

    // 查询学生已提交作业
    List<Assignment> findSubmittedAssignmentsByStudentId(Long studentId);

    // 查询所有作业
    List<Assignment> findAll();

    // 插入作业
    int insert(Assignment assignment);

    // 更新作业
    int update(Assignment assignment);

    // 删除作业
    int delete(Long id);

    // 根据截止日期查询作业
    List<Assignment> findByDeadline(Date startDate, Date endDate);

    // 更新作业状态
    int updateStatus(Long id, String status);

    // 根据教师ID查询作业
    List<Assignment> findByTeacherId(Long teacherId);

    // 查询即将截止的作业（例如7天内）
    List<Assignment> findUpcomingAssignments(int days);
}
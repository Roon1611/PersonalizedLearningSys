package com.personalizedlearning.system.service;

import com.personalizedlearning.system.entity.AssignmentSubmission;

import java.util.List;

public interface AssignmentSubmissionService {
    // 根据ID查询作业提交
    AssignmentSubmission findById(Long id);

    // 根据作业ID查询提交
    List<AssignmentSubmission> findByAssignmentId(Long assignmentId);

    // 根据学生ID和作业ID查询提交
    AssignmentSubmission findByStudentIdAndAssignmentId(Long studentId, Long assignmentId);

    // 根据学生ID查询提交
    List<AssignmentSubmission> findByStudentId(Long studentId);

    // 查询所有提交
    List<AssignmentSubmission> findAll();

    // 插入提交
    int insert(AssignmentSubmission submission);

    // 更新提交
    int update(AssignmentSubmission submission);

    // 删除提交
    int delete(Long id);

    // 更新提交状态和成绩
    int updateStatusAndScore(Long id, String status, Double score, String feedback);

    // 根据状态查询提交
    List<AssignmentSubmission> findByStatus(String status);

    // 提交作业
    boolean submitAssignment(Long studentId, Long assignmentId, String fileUrl, String content);

    // 教师批改作业
    boolean gradeAssignment(Long submissionId, Double score, String feedback);

    // 查询未批改的作业提交
    List<AssignmentSubmission> findUngradedSubmissions();
}
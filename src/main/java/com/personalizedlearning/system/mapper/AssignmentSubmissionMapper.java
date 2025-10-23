package com.personalizedlearning.system.mapper;

import com.personalizedlearning.system.entity.AssignmentSubmission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AssignmentSubmissionMapper {
    // 根据ID查询作业提交
    AssignmentSubmission findById(@Param("id") Long id);

    // 根据作业ID查询提交
    List<AssignmentSubmission> findByAssignmentId(@Param("assignmentId") Long assignmentId);

    // 根据学生ID和作业ID查询提交
    AssignmentSubmission findByStudentIdAndAssignmentId(@Param("studentId") Long studentId, @Param("assignmentId") Long assignmentId);

    // 根据学生ID查询提交
    List<AssignmentSubmission> findByStudentId(@Param("studentId") Long studentId);

    // 查询所有提交
    List<AssignmentSubmission> findAll();

    // 插入提交
    int insert(AssignmentSubmission submission);

    // 更新提交
    int update(AssignmentSubmission submission);

    // 删除提交
    int delete(@Param("id") Long id);

    // 更新提交状态和成绩
    int updateStatusAndScore(@Param("id") Long id, @Param("status") String status, @Param("score") Double score, @Param("feedback") String feedback);

    // 根据状态查询提交
    List<AssignmentSubmission> findByStatus(@Param("status") String status);
}
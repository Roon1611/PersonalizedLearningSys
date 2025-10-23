package com.personalizedlearning.system.mapper;

import com.personalizedlearning.system.entity.Assignment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AssignmentMapper {
    // 根据ID查询作业
    Assignment findById(@Param("id") Long id);

    // 根据课程ID查询作业
    List<Assignment> findByCourseId(@Param("courseId") Long courseId);

    // 查询学生待提交作业
    List<Assignment> findPendingAssignmentsByStudentId(@Param("studentId") Long studentId);

    // 查询学生已提交作业
    List<Assignment> findSubmittedAssignmentsByStudentId(@Param("studentId") Long studentId);

    // 查询所有作业
    List<Assignment> findAll();

    // 插入作业
    int insert(Assignment assignment);

    // 更新作业
    int update(Assignment assignment);

    // 删除作业
    int delete(@Param("id") Long id);

    // 根据截止日期查询作业
    List<Assignment> findByDeadline(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // 更新作业状态
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
package com.personalizedlearning.system.mapper;

import com.personalizedlearning.system.entity.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GradeMapper {
    // 根据ID查询成绩
    Grade findById(@Param("id") Long id);

    // 根据学生ID查询成绩
    List<Grade> findByStudentId(@Param("studentId") Long studentId);

    // 根据课程ID查询成绩
    List<Grade> findByCourseId(@Param("courseId") Long courseId);

    // 根据学生ID和课程ID查询成绩
    Grade findByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    // 查询所有成绩
    List<Grade> findAll();

    // 插入成绩
    int insert(Grade grade);

    // 更新成绩
    int update(Grade grade);

    // 删除成绩
    int delete(@Param("id") Long id);

    // 批量插入或更新成绩
    int batchInsertOrUpdate(@Param("grades") List<Grade> grades);

    // 根据成绩范围查询
    List<Grade> findByScoreRange(@Param("minScore") Double minScore, @Param("maxScore") Double maxScore);
}
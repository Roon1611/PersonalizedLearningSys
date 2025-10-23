package com.personalizedlearning.system.mapper;

import com.personalizedlearning.system.entity.LearningProgress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LearningProgressMapper {
    // 根据ID查询学习进度
    LearningProgress findById(@Param("id") Long id);

    // 根据学生ID和课程ID查询学习进度
    LearningProgress findByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    // 根据学生ID查询所有课程的学习进度
    List<LearningProgress> findByStudentId(@Param("studentId") Long studentId);

    // 根据课程ID查询所有学生的学习进度
    List<LearningProgress> findByCourseId(@Param("courseId") Long courseId);

    // 查询所有学习进度
    List<LearningProgress> findAll();

    // 插入学习进度
    int insert(LearningProgress progress);

    // 更新学习进度
    int update(LearningProgress progress);

    // 删除学习进度
    int delete(@Param("id") Long id);

    // 批量更新学习进度
    int batchUpdate(@Param("progresses") List<LearningProgress> progresses);

    // 根据完成度查询学习进度
    List<LearningProgress> findByCompletionRate(@Param("minRate") Integer minRate, @Param("maxRate") Integer maxRate);
}
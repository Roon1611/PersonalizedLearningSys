package com.personalizedlearning.system.mapper;

import com.personalizedlearning.system.entity.LearningRisk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LearningRiskMapper {
    // 根据ID查询学习风险
    LearningRisk findById(@Param("id") Long id);

    // 根据学生ID查询学习风险
    List<LearningRisk> findByStudentId(@Param("studentId") Long studentId);

    // 根据课程ID查询学习风险
    List<LearningRisk> findByCourseId(@Param("courseId") Long courseId);

    // 根据处理状态查询学习风险
    List<LearningRisk> findByProcessedStatus(@Param("isProcessed") Boolean isProcessed);

    // 查询所有学习风险
    List<LearningRisk> findAll();

    // 插入学习风险
    int insert(LearningRisk risk);

    // 更新学习风险
    int update(LearningRisk risk);

    // 删除学习风险
    int delete(@Param("id") Long id);

    // 更新风险处理状态
    int updateProcessedStatus(@Param("id") Long id, @Param("isProcessed") Boolean isProcessed);

    // 根据风险百分比范围查询
    List<LearningRisk> findByRiskPercentageRange(@Param("minPercentage") Integer minPercentage, @Param("maxPercentage") Integer maxPercentage);

    // 查询未处理的高风险学生（风险百分比>70）
    List<LearningRisk> findUnprocessedHighRiskStudents();
}
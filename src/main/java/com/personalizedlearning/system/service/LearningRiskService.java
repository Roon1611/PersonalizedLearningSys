package com.personalizedlearning.system.service;

import com.personalizedlearning.system.entity.LearningRisk;

import java.util.List;

public interface LearningRiskService {
    // 根据ID查询学习风险
    LearningRisk findById(Long id);

    // 根据学生ID查询学习风险
    List<LearningRisk> findByStudentId(Long studentId);

    // 根据课程ID查询学习风险
    List<LearningRisk> findByCourseId(Long courseId);

    // 根据处理状态查询学习风险
    List<LearningRisk> findByProcessedStatus(Boolean isProcessed);

    // 查询所有学习风险
    List<LearningRisk> findAll();

    // 插入学习风险
    int insert(LearningRisk risk);

    // 更新学习风险
    int update(LearningRisk risk);

    // 删除学习风险
    int delete(Long id);

    // 更新风险处理状态
    int updateProcessedStatus(Long id, Boolean isProcessed);

    // 根据风险百分比范围查询
    List<LearningRisk> findByRiskPercentageRange(Integer minPercentage, Integer maxPercentage);

    // 查询未处理的高风险学生（风险百分比>70）
    List<LearningRisk> findUnprocessedHighRiskStudents();

    // 评估学生学习风险
    LearningRisk evaluateLearningRisk(Long studentId, Long courseId);

    // 批量评估学习风险
    boolean batchEvaluateLearningRisks(Long courseId);

    // 获取学习风险统计信息
    RiskStatistics getRiskStatisticsByCourseId(Long courseId);

    // 内部类，用于存储学习风险统计信息
    class RiskStatistics {
        private Integer totalRisks;
        private Integer highRiskCount;
        private Integer mediumRiskCount;
        private Integer lowRiskCount;
        private Integer unprocessedCount;
        private Double averageRiskPercentage;

        // 构造函数、getter和setter方法
        public RiskStatistics() {
        }

        public RiskStatistics(Integer totalRisks, Integer highRiskCount, 
                            Integer mediumRiskCount, Integer lowRiskCount, 
                            Integer unprocessedCount, Double averageRiskPercentage) {
            this.totalRisks = totalRisks;
            this.highRiskCount = highRiskCount;
            this.mediumRiskCount = mediumRiskCount;
            this.lowRiskCount = lowRiskCount;
            this.unprocessedCount = unprocessedCount;
            this.averageRiskPercentage = averageRiskPercentage;
        }

        public Integer getTotalRisks() {
            return totalRisks;
        }

        public void setTotalRisks(Integer totalRisks) {
            this.totalRisks = totalRisks;
        }

        public Integer getHighRiskCount() {
            return highRiskCount;
        }

        public void setHighRiskCount(Integer highRiskCount) {
            this.highRiskCount = highRiskCount;
        }

        public Integer getMediumRiskCount() {
            return mediumRiskCount;
        }

        public void setMediumRiskCount(Integer mediumRiskCount) {
            this.mediumRiskCount = mediumRiskCount;
        }

        public Integer getLowRiskCount() {
            return lowRiskCount;
        }

        public void setLowRiskCount(Integer lowRiskCount) {
            this.lowRiskCount = lowRiskCount;
        }

        public Integer getUnprocessedCount() {
            return unprocessedCount;
        }

        public void setUnprocessedCount(Integer unprocessedCount) {
            this.unprocessedCount = unprocessedCount;
        }

        public Double getAverageRiskPercentage() {
            return averageRiskPercentage;
        }

        public void setAverageRiskPercentage(Double averageRiskPercentage) {
            this.averageRiskPercentage = averageRiskPercentage;
        }
    }
}
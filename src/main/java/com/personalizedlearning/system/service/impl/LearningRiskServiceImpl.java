package com.personalizedlearning.system.service.impl;

import com.personalizedlearning.system.entity.LearningRisk;
import com.personalizedlearning.system.mapper.LearningRiskMapper;
import com.personalizedlearning.system.service.LearningProgressService;
import com.personalizedlearning.system.service.LearningRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LearningRiskServiceImpl implements LearningRiskService {

    @Autowired
    private LearningRiskMapper riskMapper;

    @Autowired
    private LearningProgressService progressService;

    @Override
    public LearningRisk findById(Long id) {
        return riskMapper.findById(id);
    }

    @Override
    public List<LearningRisk> findByStudentId(Long studentId) {
        return riskMapper.findByStudentId(studentId);
    }

    @Override
    public List<LearningRisk> findByCourseId(Long courseId) {
        return riskMapper.findByCourseId(courseId);
    }

    @Override
    public List<LearningRisk> findByProcessedStatus(Boolean isProcessed) {
        return riskMapper.findByProcessedStatus(isProcessed);
    }

    @Override
    public List<LearningRisk> findAll() {
        return riskMapper.findAll();
    }

    @Override
    @Transactional
    public int insert(LearningRisk risk) {
        risk.setCreatedAt(new Date());
        risk.setUpdatedAt(new Date());
        if (risk.getIsProcessed() == null) {
            risk.setIsProcessed(false);
        }
        return riskMapper.insert(risk);
    }

    @Override
    @Transactional
    public int update(LearningRisk risk) {
        risk.setUpdatedAt(new Date());
        return riskMapper.update(risk);
    }

    @Override
    @Transactional
    public int delete(Long id) {
        return riskMapper.delete(id);
    }

    @Override
    @Transactional
    public int updateProcessedStatus(Long id, Boolean isProcessed) {
        return riskMapper.updateProcessedStatus(id, isProcessed);
    }

    @Override
    public List<LearningRisk> findByRiskPercentageRange(Integer minPercentage, Integer maxPercentage) {
        return riskMapper.findByRiskPercentageRange(minPercentage, maxPercentage);
    }

    @Override
    public List<LearningRisk> findUnprocessedHighRiskStudents() {
        return riskMapper.findUnprocessedHighRiskStudents();
    }

    @Override
    @Transactional
    public LearningRisk evaluateLearningRisk(Long studentId, Long courseId) {
        // 1. 获取学生的学习进度
        // 这里简化实现，实际应用中可能需要更复杂的风险评估算法
        // 例如基于完成度、测验成绩、学习时长、最后访问时间等多种因素
        
        // 2. 模拟风险评估逻辑
        LearningRisk risk = new LearningRisk();
        risk.setStudentId(studentId);
        risk.setCourseId(courseId);
        
        // 随机生成风险百分比（实际应用中应基于真实数据计算）
        int riskPercentage = new Random().nextInt(100);
        risk.setRiskPercentage(riskPercentage);
        
        // 根据风险百分比设置原因
        String reason;
        if (riskPercentage > 70) {
            reason = "学习进度落后，测验成绩偏低";
        } else if (riskPercentage > 40) {
            reason = "学习进度一般，需要增加学习时间";
        } else {
            reason = "学习状态良好";
        }
        risk.setReason(reason);
        
        // 设置推荐建议
        List<String> recommendations = new ArrayList<>();
        if (riskPercentage > 70) {
            recommendations.add("安排额外的学习时间");
            recommendations.add("参加辅导课程");
            recommendations.add("与教师沟通寻求帮助");
        } else if (riskPercentage > 40) {
            recommendations.add("定期复习课程内容");
            recommendations.add("完成所有练习作业");
            recommendations.add("加入学习小组");
        } else {
            recommendations.add("保持当前学习状态");
            recommendations.add("帮助其他同学学习");
            recommendations.add("探索相关扩展资源");
        }
        
        // 将推荐列表转换为JSON字符串存储
        risk.setRecommendations(String.join(",", recommendations));
        risk.setRecommendationList(recommendations);
        
        risk.setIsProcessed(false);
        risk.setCreatedAt(new Date());
        risk.setUpdatedAt(new Date());
        
        // 3. 保存评估结果
        riskMapper.insert(risk);
        
        return risk;
    }

    @Override
    @Transactional
    public boolean batchEvaluateLearningRisks(Long courseId) {
        // 在实际实现中，这里应该获取课程中的所有学生，然后逐一评估风险
        // 目前简化实现，返回true表示评估成功
        return true;
    }

    @Override
    public RiskStatistics getRiskStatisticsByCourseId(Long courseId) {
        List<LearningRisk> risks = riskMapper.findByCourseId(courseId);
        if (risks == null || risks.isEmpty()) {
            return new RiskStatistics(0, 0, 0, 0, 0, 0.0);
        }

        int totalRisks = risks.size();
        int highRiskCount = 0;
        int mediumRiskCount = 0;
        int lowRiskCount = 0;
        int unprocessedCount = 0;
        int sumRiskPercentage = 0;

        for (LearningRisk risk : risks) {
            Integer percentage = risk.getRiskPercentage();
            if (percentage != null) {
                sumRiskPercentage += percentage;
                if (percentage > 70) {
                    highRiskCount++;
                } else if (percentage > 40) {
                    mediumRiskCount++;
                } else {
                    lowRiskCount++;
                }
            }
            if (Boolean.FALSE.equals(risk.getIsProcessed())) {
                unprocessedCount++;
            }
        }

        double averageRiskPercentage = totalRisks > 0 ? (double) sumRiskPercentage / totalRisks : 0.0;

        return new RiskStatistics(totalRisks, highRiskCount, mediumRiskCount, lowRiskCount, 
                               unprocessedCount, averageRiskPercentage);
    }
}
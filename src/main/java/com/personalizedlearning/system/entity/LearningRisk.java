package com.personalizedlearning.system.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LearningRisk {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Integer riskPercentage;
    private String reason;
    private String recommendations; // 存储为JSON字符串
    private Boolean isProcessed;
    private Date createdAt;
    private Date updatedAt;

    // 非数据库字段
    private String studentName;
    private String courseName;
    private List<String> recommendationList; // 解析后的推荐列表
}
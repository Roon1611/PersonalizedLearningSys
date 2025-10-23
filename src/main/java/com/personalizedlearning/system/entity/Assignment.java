package com.personalizedlearning.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Assignment {
    private Long id;
    private Long courseId;
    private String title;
    private String description;
    private String category;
    private Date deadline;
    private Integer estimatedTime; // 预计完成时间（分钟）
    private Date createdAt;
    private Date updatedAt;
    private String status; // active, closed, archived

    // 非数据库字段
    private String courseName;
    private Integer doubtCount; // 答疑请求数
    private Integer submissionCount; // 提交人数
}
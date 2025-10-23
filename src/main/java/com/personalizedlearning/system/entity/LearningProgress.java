package com.personalizedlearning.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LearningProgress {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Integer chapterId;
    private Integer progress;
    private Date lastLearnedAt;
    private Date createdAt;
    private Date updatedAt;

    // 非数据库字段
    private String studentName;
    private String courseName;
}
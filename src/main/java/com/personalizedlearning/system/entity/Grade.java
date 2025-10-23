package com.personalizedlearning.system.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Grade {
    private Long id;
    private Long studentId;
    private Long courseId;
    private BigDecimal score;
    private String examType; // midterm, final, quiz, assignment
    private String examName;
    private Date examDate;
    private Date createdAt;
    private Date updatedAt;

    // 非数据库字段
    private String studentName;
    private String courseName;
}
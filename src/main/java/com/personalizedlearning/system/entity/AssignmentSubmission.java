package com.personalizedlearning.system.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AssignmentSubmission {
    private Long id;
    private Long assignmentId;
    private Long studentId;
    private Date submitTime;
    private String filePath;
    private String status; // submitted, graded, pending
    private BigDecimal score;
    private String feedback;

    // 非数据库字段
    private String studentName;
    private String assignmentTitle;
    private String courseName;
    private Integer attachmentCount;
}
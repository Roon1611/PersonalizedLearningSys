package com.personalizedlearning.system.entity;

import lombok.Data;
import java.util.Date;

/**
 * 学生课程关系实体类
 */
@Data
public class StudentCourse {
    private Long id;
    private Long studentId; // 学生ID
    private Long courseId; // 课程ID
    private Date enrollDate; // 报名日期
    private Boolean isActive; // 是否活跃
}
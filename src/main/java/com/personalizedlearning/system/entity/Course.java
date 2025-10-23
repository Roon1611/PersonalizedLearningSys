package com.personalizedlearning.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Course {
    private Long id;
    private String name;
    private String description;
    private Long teacherId;
    private String coverImage;
    private Date startDate;
    private Date endDate;
    private Integer totalChapters;
    private Date createdAt;
    private Date updatedAt;
    private Integer status;

    // 非数据库字段，用于显示教师姓名
    private String teacherName;
}
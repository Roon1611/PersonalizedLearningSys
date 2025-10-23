package com.personalizedlearning.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String role; // student, teacher, admin
    private String avatar;
    private Date createdAt;
    private Date updatedAt;
    private Integer status;
}
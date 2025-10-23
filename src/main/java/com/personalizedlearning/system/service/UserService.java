package com.personalizedlearning.system.service;

import com.personalizedlearning.system.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    // 根据ID查询用户
    User findById(Long id);

    // 根据用户名查询用户
    User findByUsername(String username);

    // 根据角色查询用户
    List<User> findByRole(String role);

    // 查询所有用户
    List<User> findAll();

    // 插入用户
    int insert(User user);

    // 更新用户
    int update(User user);

    // 删除用户
    int delete(Long id);

    // 用户登录认证
    User login(String username, String password);

    // 根据课程ID查询学生列表
    List<User> findStudentsByCourseId(Long courseId);

    // 用户密码加密
    String encryptPassword(String password);
}
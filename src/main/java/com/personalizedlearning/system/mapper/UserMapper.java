package com.personalizedlearning.system.mapper;

import com.personalizedlearning.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    // 根据ID查询用户
    User findById(@Param("id") Long id);

    // 根据用户名查询用户
    User findByUsername(@Param("username") String username);

    // 根据角色查询用户
    List<User> findByRole(@Param("role") String role);

    // 查询所有用户
    List<User> findAll();

    // 插入用户
    int insert(User user);

    // 更新用户
    int update(User user);

    // 删除用户
    int delete(@Param("id") Long id);

    // 根据课程ID查询学生列表
    List<User> findStudentsByCourseId(@Param("courseId") Long courseId);
}
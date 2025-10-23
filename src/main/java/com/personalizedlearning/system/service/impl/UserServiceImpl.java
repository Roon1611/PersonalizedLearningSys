package com.personalizedlearning.system.service.impl;

import com.personalizedlearning.system.entity.User;
import com.personalizedlearning.system.mapper.UserMapper;
import com.personalizedlearning.system.service.RabbitMQMessageSender;
import com.personalizedlearning.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RabbitMQMessageSender messageSender;

    @Override
    @Cacheable(value = "user", key = "#id")
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    @Cacheable(value = "users", key = "#role")
    public List<User> findByRole(String role) {
        return userMapper.findByRole(role);
    }

    @Override
    @Cacheable(value = "users", key = "'all'")
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    @CacheEvict(value = {"users"}, key = "'all'", allEntries = true)
    public int insert(User user) {
        // 密码加密后再存储
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(encryptPassword(user.getPassword()));
        }
        int result = userMapper.insert(user);
        
        // 发送用户注册消息
        if (result > 0) {
            Map<String, Object> message = new HashMap<>();
            message.put("userId", user.getId());
            message.put("username", user.getUsername());
            message.put("role", user.getRole());
            message.put("timestamp", System.currentTimeMillis());
            messageSender.sendUserRegisteredMessage(message);
        }
        
        return result;
    }

    @Override
    @CacheEvict(value = {"user", "users"}, key = "#user.id", allEntries = true)
    public int update(User user) {
        // 如果更新时提供了新密码，进行加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(encryptPassword(user.getPassword()));
        }
        return userMapper.update(user);
    }

    @Override
    @CacheEvict(value = {"user", "users"}, key = "#id", allEntries = true)
    public int delete(Long id) {
        return userMapper.delete(id);
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null) {
            String encryptedPassword = encryptPassword(password);
            if (encryptedPassword.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    @Cacheable(value = "courseStudents", key = "#courseId")
    public List<User> findStudentsByCourseId(Long courseId) {
        return userMapper.findStudentsByCourseId(courseId);
    }

    @Override
    public String encryptPassword(String password) {
        // 使用MD5加密密码
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }
}
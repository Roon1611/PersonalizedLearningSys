package com.personalizedlearning.system.service.impl;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 简单的UserService测试类，用于验证密码加密功能
 */
public class UserServiceTest {

    public static void main(String[] args) {
        // 测试密码加密功能
        testPasswordEncryption();
    }

    private static void testPasswordEncryption() {
        System.out.println("=== 测试密码加密功能 ===");
        
        // 模拟UserServiceImpl中的encryptPassword方法
        String password = "test123";
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        
        System.out.println("原始密码: " + password);
        System.out.println("加密后密码: " + encryptedPassword);
        System.out.println("加密结果长度: " + encryptedPassword.length());
        
        // 验证两次加密结果是否一致
        String encryptedPassword2 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        System.out.println("两次加密结果是否一致: " + encryptedPassword.equals(encryptedPassword2));
    }
}
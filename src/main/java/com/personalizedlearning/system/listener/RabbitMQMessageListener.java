package com.personalizedlearning.system.listener;

import com.personalizedlearning.system.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitMQMessageListener {

    // 监听课程更新消息
    @RabbitListener(queues = RabbitMQConfig.COURSE_UPDATED_QUEUE)
    public void handleCourseUpdatedMessage(Map<String, Object> message) {
        System.out.println("接收到课程更新消息: " + message);
        // 在这里可以实现课程更新后的业务逻辑，例如：
        // 1. 更新缓存中的课程信息
        // 2. 发送通知给相关学生
        // 3. 更新学习进度统计
    }

    // 监听作业创建消息
    @RabbitListener(queues = RabbitMQConfig.ASSIGNMENT_CREATED_QUEUE)
    public void handleAssignmentCreatedMessage(Map<String, Object> message) {
        System.out.println("接收到作业创建消息: " + message);
        // 在这里可以实现作业创建后的业务逻辑，例如：
        // 1. 为所有学生创建作业记录
        // 2. 发送通知给相关学生
        // 3. 记录学习活动日志
    }

    // 监听成绩更新消息
    @RabbitListener(queues = RabbitMQConfig.GRADE_UPDATED_QUEUE)
    public void handleGradeUpdatedMessage(Map<String, Object> message) {
        System.out.println("接收到成绩更新消息: " + message);
        // 在这里可以实现成绩更新后的业务逻辑，例如：
        // 1. 更新学习进度统计
        // 2. 检查学习风险
        // 3. 发送成绩通知给学生
        // 4. 清除相关缓存
    }

    // 监听用户注册消息
    @RabbitListener(queues = RabbitMQConfig.USER_REGISTERED_QUEUE)
    public void handleUserRegisteredMessage(Map<String, Object> message) {
        System.out.println("接收到用户注册消息: " + message);
        // 在这里可以实现用户注册后的业务逻辑，例如：
        // 1. 初始化用户学习档案
        // 2. 发送欢迎邮件
        // 3. 记录用户活动日志
    }
}
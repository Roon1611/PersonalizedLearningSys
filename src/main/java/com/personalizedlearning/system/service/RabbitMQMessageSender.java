package com.personalizedlearning.system.service;

import com.personalizedlearning.system.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 发送课程更新消息
    public void sendCourseUpdatedMessage(Object message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.COURSE_EXCHANGE,
                RabbitMQConfig.COURSE_UPDATED_KEY,
                message
        );
    }

    // 发送作业创建消息
    public void sendAssignmentCreatedMessage(Object message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ASSIGNMENT_EXCHANGE,
                RabbitMQConfig.ASSIGNMENT_CREATED_KEY,
                message
        );
    }

    // 发送成绩更新消息
    public void sendGradeUpdatedMessage(Object message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.GRADE_EXCHANGE,
                RabbitMQConfig.GRADE_UPDATED_KEY,
                message
        );
    }

    // 发送用户注册消息
    public void sendUserRegisteredMessage(Object message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.USER_EXCHANGE,
                RabbitMQConfig.USER_REGISTERED_KEY,
                message
        );
    }
}
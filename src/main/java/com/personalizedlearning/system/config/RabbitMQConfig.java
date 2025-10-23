package com.personalizedlearning.system.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // 队列名称常量
    public static final String COURSE_UPDATED_QUEUE = "course-updated-queue";
    public static final String ASSIGNMENT_CREATED_QUEUE = "assignment-created-queue";
    public static final String GRADE_UPDATED_QUEUE = "grade-updated-queue";
    public static final String USER_REGISTERED_QUEUE = "user-registered-queue";
    
    // 交换机名称
    public static final String COURSE_EXCHANGE = "course-exchange";
    public static final String ASSIGNMENT_EXCHANGE = "assignment-exchange";
    public static final String GRADE_EXCHANGE = "grade-exchange";
    public static final String USER_EXCHANGE = "user-exchange";
    
    // 路由键
    public static final String COURSE_UPDATED_KEY = "course.updated";
    public static final String ASSIGNMENT_CREATED_KEY = "assignment.created";
    public static final String GRADE_UPDATED_KEY = "grade.updated";
    public static final String USER_REGISTERED_KEY = "user.registered";

    // 消息转换器
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 课程更新队列
    @Bean
    public Queue courseUpdatedQueue() {
        return new Queue(COURSE_UPDATED_QUEUE, true);
    }

    // 作业创建队列
    @Bean
    public Queue assignmentCreatedQueue() {
        return new Queue(ASSIGNMENT_CREATED_QUEUE, true);
    }

    // 成绩更新队列
    @Bean
    public Queue gradeUpdatedQueue() {
        return new Queue(GRADE_UPDATED_QUEUE, true);
    }

    // 用户注册队列
    @Bean
    public Queue userRegisteredQueue() {
        return new Queue(USER_REGISTERED_QUEUE, true);
    }

    // 课程交换机
    @Bean
    public DirectExchange courseExchange() {
        return new DirectExchange(COURSE_EXCHANGE);
    }

    // 作业交换机
    @Bean
    public DirectExchange assignmentExchange() {
        return new DirectExchange(ASSIGNMENT_EXCHANGE);
    }

    // 成绩交换机
    @Bean
    public DirectExchange gradeExchange() {
        return new DirectExchange(GRADE_EXCHANGE);
    }

    // 用户交换机
    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(USER_EXCHANGE);
    }

    // 绑定队列和交换机
    @Bean
    public Binding bindingCourseUpdated(Queue courseUpdatedQueue, DirectExchange courseExchange) {
        return BindingBuilder.bind(courseUpdatedQueue).to(courseExchange).with(COURSE_UPDATED_KEY);
    }

    @Bean
    public Binding bindingAssignmentCreated(Queue assignmentCreatedQueue, DirectExchange assignmentExchange) {
        return BindingBuilder.bind(assignmentCreatedQueue).to(assignmentExchange).with(ASSIGNMENT_CREATED_KEY);
    }

    @Bean
    public Binding bindingGradeUpdated(Queue gradeUpdatedQueue, DirectExchange gradeExchange) {
        return BindingBuilder.bind(gradeUpdatedQueue).to(gradeExchange).with(GRADE_UPDATED_KEY);
    }

    @Bean
    public Binding bindingUserRegistered(Queue userRegisteredQueue, DirectExchange userExchange) {
        return BindingBuilder.bind(userRegisteredQueue).to(userExchange).with(USER_REGISTERED_KEY);
    }
}
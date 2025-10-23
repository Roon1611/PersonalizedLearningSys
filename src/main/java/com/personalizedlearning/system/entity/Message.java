package com.personalizedlearning.system.entity;

import lombok.Data;
import java.util.Date;

/**
 * 消息实体类
 */
@Data
public class Message {
    private Long id;
    private Long senderId; // 发送者ID
    private Long receiverId; // 接收者ID
    private String subject; // 主题
    private String content; // 内容
    private Boolean isRead; // 是否已读
    private Date createdAt; // 创建时间
    private Date updatedAt; // 更新时间
}
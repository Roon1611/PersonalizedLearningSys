package com.personalizedlearning.system.entity;

import lombok.Data;
import java.util.Date;

/**
 * 聊天消息实体类
 */
@Data
public class ChatMessage {
    private Long id;
    private Long senderId; // 发送者ID
    private String chatId; // 聊天ID
    private String content; // 消息内容
    private Boolean isRead; // 是否已读
    private Date createdAt; // 创建时间
}
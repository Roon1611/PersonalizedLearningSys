package com.personalizedlearning.system.service;

import com.personalizedlearning.system.entity.Message;
import java.util.List;
import java.util.Map;

/**
 * 消息服务接口
 */
public interface MessageService {
    int addMessage(Message message);
    int updateMessage(Message message);
    int deleteMessage(Long id);
    Message getMessageById(Long id);
    List<Message> getAllMessages();
    List<Message> getMessagesBySenderId(Long senderId);
    List<Message> getMessagesByReceiverId(Long receiverId);
    List<Message> getMessagesBySenderAndReceiver(Long senderId, Long receiverId);
    int countUnreadMessages(Long receiverId);
    int markMessageAsRead(Long id);
    int markAllMessagesAsRead(Long receiverId);
    int getTotalMessages();
    int getSentMessagesCount(Long userId);
    int getReceivedMessagesCount(Long userId);
    List<Message> getMessagesByDateRange(Map<String, Object> params);
    List<Message> getConversation(Long user1Id, Long user2Id);
    List<Message> getUserMessagesByPage(Map<String, Object> params);
    List<Message> getUserSentMessagesByPage(Map<String, Object> params);
    List<Message> getMessagesByPage(Map<String, Object> params);
    int getMessagesCount();
}
package com.personalizedlearning.system.mapper;

import com.personalizedlearning.system.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 消息Mapper接口
 */
@Mapper
public interface MessageMapper {
    int insert(Message message);
    int update(Message message);
    int delete(Long id);
    Message findById(Long id);
    List<Message> findAll();
    List<Message> findBySenderId(Long senderId);
    List<Message> findByReceiverId(Long receiverId);
    List<Message> findBySenderAndReceiver(Map<String, Object> params);
    int countUnreadMessages(Long receiverId);
    int markAsRead(Long id);
    int markAllAsRead(Long receiverId);
    int getTotalMessages();
    int getSentMessagesCount(Long userId);
    int getReceivedMessagesCount(Long userId);
    List<Message> findByDateRange(Map<String, Object> params);
    List<Message> findConversation(Map<String, Object> params);
    List<Message> findUserMessagesByPage(Map<String, Object> params);
    List<Message> findUserSentMessagesByPage(Map<String, Object> params);
    List<Message> findByPage(Map<String, Object> params);
    int count();
}
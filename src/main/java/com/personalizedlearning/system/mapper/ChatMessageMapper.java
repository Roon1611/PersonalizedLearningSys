package com.personalizedlearning.system.mapper;

import com.personalizedlearning.system.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 聊天消息Mapper接口
 */
@Mapper
public interface ChatMessageMapper {
    int insert(ChatMessage chatMessage);
    int update(ChatMessage chatMessage);
    int delete(Long id);
    ChatMessage findById(Long id);
    List<ChatMessage> findAll();
    List<ChatMessage> findBySenderId(Long senderId);
    List<ChatMessage> findByChatId(String chatId);
    int countUnreadChatMessages(String chatId);
    int markAsRead(Map<String, Object> params);
    int getTotalChatMessages();
    int getTotalChats();
    int getUserChatsCount(Long userId);
    List<ChatMessage> findByDateRange(Map<String, Object> params);
    List<ChatMessage> findLatestMessages(Integer limit);
    List<ChatMessage> findByPage(Map<String, Object> params);
    int count();
}
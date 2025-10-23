package com.personalizedlearning.system.service;

import com.personalizedlearning.system.entity.ChatMessage;
import java.util.List;
import java.util.Map;

/**
 * 聊天消息服务接口
 */
public interface ChatMessageService {
    int addChatMessage(ChatMessage chatMessage);
    int updateChatMessage(ChatMessage chatMessage);
    int deleteChatMessage(Long id);
    ChatMessage getChatMessageById(Long id);
    List<ChatMessage> getAllChatMessages();
    List<ChatMessage> getChatMessagesBySenderId(Long senderId);
    List<ChatMessage> getChatMessagesByChatId(String chatId);
    int countUnreadChatMessages(String chatId);
    int markChatMessagesAsRead(String chatId, Long userId);
    int getTotalChatMessages();
    int getTotalChats();
    int getUserChatsCount(Long userId);
    List<ChatMessage> getChatMessagesByDateRange(Map<String, Object> params);
    List<ChatMessage> getLatestChatMessages(Integer limit);
    List<ChatMessage> getChatMessagesByPage(Map<String, Object> params);
    int getChatMessagesCount();
}
package com.personalizedlearning.system.service.impl;

import com.personalizedlearning.system.entity.ChatMessage;
import com.personalizedlearning.system.mapper.ChatMessageMapper;
import com.personalizedlearning.system.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天消息服务实现类
 */
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Override
    public int addChatMessage(ChatMessage chatMessage) {
        return chatMessageMapper.insert(chatMessage);
    }

    @Override
    public int updateChatMessage(ChatMessage chatMessage) {
        return chatMessageMapper.update(chatMessage);
    }

    @Override
    public int deleteChatMessage(Long id) {
        return chatMessageMapper.delete(id);
    }

    @Override
    public ChatMessage getChatMessageById(Long id) {
        return chatMessageMapper.findById(id);
    }

    @Override
    public List<ChatMessage> getAllChatMessages() {
        return chatMessageMapper.findAll();
    }

    @Override
    public List<ChatMessage> getChatMessagesBySenderId(Long senderId) {
        return chatMessageMapper.findBySenderId(senderId);
    }

    @Override
    public List<ChatMessage> getChatMessagesByChatId(String chatId) {
        return chatMessageMapper.findByChatId(chatId);
    }

    @Override
    public int countUnreadChatMessages(String chatId) {
        return chatMessageMapper.countUnreadChatMessages(chatId);
    }

    @Override
    public int markChatMessagesAsRead(String chatId, Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("chatId", chatId);
        params.put("userId", userId);
        return chatMessageMapper.markAsRead(params);
    }

    @Override
    public int getTotalChatMessages() {
        return chatMessageMapper.getTotalChatMessages();
    }

    @Override
    public int getTotalChats() {
        return chatMessageMapper.getTotalChats();
    }

    @Override
    public int getUserChatsCount(Long userId) {
        return chatMessageMapper.getUserChatsCount(userId);
    }

    @Override
    public List<ChatMessage> getChatMessagesByDateRange(Map<String, Object> params) {
        return chatMessageMapper.findByDateRange(params);
    }

    @Override
    public List<ChatMessage> getLatestChatMessages(Integer limit) {
        return chatMessageMapper.findLatestMessages(limit);
    }

    @Override
    public List<ChatMessage> getChatMessagesByPage(Map<String, Object> params) {
        return chatMessageMapper.findByPage(params);
    }

    @Override
    public int getChatMessagesCount() {
        return chatMessageMapper.count();
    }
}
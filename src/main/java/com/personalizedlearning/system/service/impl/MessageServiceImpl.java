package com.personalizedlearning.system.service.impl;

import com.personalizedlearning.system.entity.Message;
import com.personalizedlearning.system.mapper.MessageMapper;
import com.personalizedlearning.system.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息服务实现类
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public int addMessage(Message message) {
        return messageMapper.insert(message);
    }

    @Override
    public int updateMessage(Message message) {
        return messageMapper.update(message);
    }

    @Override
    public int deleteMessage(Long id) {
        return messageMapper.delete(id);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageMapper.findById(id);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageMapper.findAll();
    }

    @Override
    public List<Message> getMessagesBySenderId(Long senderId) {
        return messageMapper.findBySenderId(senderId);
    }

    @Override
    public List<Message> getMessagesByReceiverId(Long receiverId) {
        return messageMapper.findByReceiverId(receiverId);
    }

    @Override
    public List<Message> getMessagesBySenderAndReceiver(Long senderId, Long receiverId) {
        Map<String, Object> params = new HashMap<>();
        params.put("senderId", senderId);
        params.put("receiverId", receiverId);
        return messageMapper.findBySenderAndReceiver(params);
    }

    @Override
    public int countUnreadMessages(Long receiverId) {
        return messageMapper.countUnreadMessages(receiverId);
    }

    @Override
    public int markMessageAsRead(Long id) {
        return messageMapper.markAsRead(id);
    }

    @Override
    public int markAllMessagesAsRead(Long receiverId) {
        return messageMapper.markAllAsRead(receiverId);
    }

    @Override
    public int getTotalMessages() {
        return messageMapper.getTotalMessages();
    }

    @Override
    public int getSentMessagesCount(Long userId) {
        return messageMapper.getSentMessagesCount(userId);
    }

    @Override
    public int getReceivedMessagesCount(Long userId) {
        return messageMapper.getReceivedMessagesCount(userId);
    }

    @Override
    public List<Message> getMessagesByDateRange(Map<String, Object> params) {
        return messageMapper.findByDateRange(params);
    }

    @Override
    public List<Message> getConversation(Long user1Id, Long user2Id) {
        Map<String, Object> params = new HashMap<>();
        params.put("user1Id", user1Id);
        params.put("user2Id", user2Id);
        return messageMapper.findConversation(params);
    }

    @Override
    public List<Message> getUserMessagesByPage(Map<String, Object> params) {
        return messageMapper.findUserMessagesByPage(params);
    }

    @Override
    public List<Message> getUserSentMessagesByPage(Map<String, Object> params) {
        return messageMapper.findUserSentMessagesByPage(params);
    }

    @Override
    public List<Message> getMessagesByPage(Map<String, Object> params) {
        return messageMapper.findByPage(params);
    }

    @Override
    public int getMessagesCount() {
        return messageMapper.count();
    }
}
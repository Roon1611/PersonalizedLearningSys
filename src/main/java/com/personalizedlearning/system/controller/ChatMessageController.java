package com.personalizedlearning.system.controller;

import com.personalizedlearning.system.entity.ChatMessage;
import com.personalizedlearning.system.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天消息控制器
 */
@RestController
@RequestMapping("/api/chat-messages")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    /**
     * 添加聊天消息
     */
    @PostMapping
    public Map<String, Object> addChatMessage(@RequestBody ChatMessage chatMessage) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = chatMessageService.addChatMessage(chatMessage);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 更新聊天消息
     */
    @PutMapping
    public Map<String, Object> updateChatMessage(@RequestBody ChatMessage chatMessage) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = chatMessageService.updateChatMessage(chatMessage);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "更新成功" : "更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除聊天消息
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteChatMessage(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = chatMessageService.deleteChatMessage(id);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "删除成功" : "删除失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据ID获取聊天消息
     */
    @GetMapping("/{id}")
    public Map<String, Object> getChatMessageById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            ChatMessage chatMessage = chatMessageService.getChatMessageById(id);
            result.put("success", chatMessage != null);
            result.put("data", chatMessage);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取所有聊天消息
     */
    @GetMapping
    public Map<String, Object> getAllChatMessages() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ChatMessage> chatMessages = chatMessageService.getAllChatMessages();
            result.put("success", true);
            result.put("data", chatMessages);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据发送者ID获取聊天消息
     */
    @GetMapping("/sender/{senderId}")
    public Map<String, Object> getChatMessagesBySenderId(@PathVariable Long senderId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ChatMessage> chatMessages = chatMessageService.getChatMessagesBySenderId(senderId);
            result.put("success", true);
            result.put("data", chatMessages);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据聊天ID获取聊天消息
     */
    @GetMapping("/chat/{chatId}")
    public Map<String, Object> getChatMessagesByChatId(@PathVariable String chatId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ChatMessage> chatMessages = chatMessageService.getChatMessagesByChatId(chatId);
            result.put("success", true);
            result.put("data", chatMessages);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取未读聊天消息数量
     */
    @GetMapping("/unread/count/{chatId}")
    public Map<String, Object> countUnreadChatMessages(@PathVariable String chatId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = chatMessageService.countUnreadChatMessages(chatId);
            result.put("success", true);
            result.put("count", count);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 标记聊天消息为已读
     */
    @PutMapping("/read/{chatId}/{userId}")
    public Map<String, Object> markChatMessagesAsRead(@PathVariable String chatId, @PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = chatMessageService.markChatMessagesAsRead(chatId, userId);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "标记成功" : "标记失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "标记失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取聊天统计信息
     */
    @GetMapping("/stats")
    public Map<String, Object> getChatStats() {
        Map<String, Object> result = new HashMap<>();
        try {
            int totalMessages = chatMessageService.getTotalChatMessages();
            int totalChats = chatMessageService.getTotalChats();
            Map<String, Integer> stats = new HashMap<>();
            stats.put("totalMessages", totalMessages);
            stats.put("totalChats", totalChats);
            result.put("success", true);
            result.put("data", stats);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取最新聊天消息
     */
    @GetMapping("/latest/{limit}")
    public Map<String, Object> getLatestChatMessages(@PathVariable Integer limit) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ChatMessage> chatMessages = chatMessageService.getLatestChatMessages(limit);
            result.put("success", true);
            result.put("data", chatMessages);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 分页获取聊天消息
     */
    @GetMapping("/page")
    public Map<String, Object> getChatMessagesByPage(@RequestParam int page, @RequestParam int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("offset", (page - 1) * pageSize);
            params.put("pageSize", pageSize);
            List<ChatMessage> chatMessages = chatMessageService.getChatMessagesByPage(params);
            int total = chatMessageService.getChatMessagesCount();
            result.put("success", true);
            result.put("data", chatMessages);
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }
}
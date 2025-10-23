package com.personalizedlearning.system.controller;

import com.personalizedlearning.system.entity.Message;
import com.personalizedlearning.system.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息控制器
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 添加消息
     */
    @PostMapping
    public Map<String, Object> addMessage(@RequestBody Message message) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = messageService.addMessage(message);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 更新消息
     */
    @PutMapping
    public Map<String, Object> updateMessage(@RequestBody Message message) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = messageService.updateMessage(message);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "更新成功" : "更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除消息
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteMessage(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = messageService.deleteMessage(id);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "删除成功" : "删除失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据ID获取消息
     */
    @GetMapping("/{id}")
    public Map<String, Object> getMessageById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Message message = messageService.getMessageById(id);
            result.put("success", message != null);
            result.put("data", message);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取所有消息
     */
    @GetMapping
    public Map<String, Object> getAllMessages() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Message> messages = messageService.getAllMessages();
            result.put("success", true);
            result.put("data", messages);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据发送者ID获取消息
     */
    @GetMapping("/sender/{senderId}")
    public Map<String, Object> getMessagesBySenderId(@PathVariable Long senderId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Message> messages = messageService.getMessagesBySenderId(senderId);
            result.put("success", true);
            result.put("data", messages);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据接收者ID获取消息
     */
    @GetMapping("/receiver/{receiverId}")
    public Map<String, Object> getMessagesByReceiverId(@PathVariable Long receiverId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Message> messages = messageService.getMessagesByReceiverId(receiverId);
            result.put("success", true);
            result.put("data", messages);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取发送者与接收者之间的消息
     */
    @GetMapping("/conversation/{senderId}/{receiverId}")
    public Map<String, Object> getMessagesBySenderAndReceiver(@PathVariable Long senderId, @PathVariable Long receiverId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Message> messages = messageService.getMessagesBySenderAndReceiver(senderId, receiverId);
            result.put("success", true);
            result.put("data", messages);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取未读消息数量
     */
    @GetMapping("/unread/count/{receiverId}")
    public Map<String, Object> countUnreadMessages(@PathVariable Long receiverId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = messageService.countUnreadMessages(receiverId);
            result.put("success", true);
            result.put("count", count);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 标记消息为已读
     */
    @PutMapping("/read/{id}")
    public Map<String, Object> markMessageAsRead(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = messageService.markMessageAsRead(id);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "标记成功" : "标记失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "标记失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 标记所有消息为已读
     */
    @PutMapping("/read/all/{receiverId}")
    public Map<String, Object> markAllMessagesAsRead(@PathVariable Long receiverId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = messageService.markAllMessagesAsRead(receiverId);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "标记成功" : "标记失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "标记失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取消息统计信息
     */
    @GetMapping("/stats")
    public Map<String, Object> getMessageStats() {
        Map<String, Object> result = new HashMap<>();
        try {
            int totalMessages = messageService.getTotalMessages();
            Map<String, Integer> stats = new HashMap<>();
            stats.put("totalMessages", totalMessages);
            result.put("success", true);
            result.put("data", stats);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取用户消息统计
     */
    @GetMapping("/stats/user/{userId}")
    public Map<String, Object> getUserMessageStats(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int sentMessagesCount = messageService.getSentMessagesCount(userId);
            int receivedMessagesCount = messageService.getReceivedMessagesCount(userId);
            Map<String, Integer> stats = new HashMap<>();
            stats.put("sentMessagesCount", sentMessagesCount);
            stats.put("receivedMessagesCount", receivedMessagesCount);
            result.put("success", true);
            result.put("data", stats);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 分页获取用户消息
     */
    @GetMapping("/page/user")
    public Map<String, Object> getUserMessagesByPage(@RequestParam Long userId, @RequestParam int page, @RequestParam int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("offset", (page - 1) * pageSize);
            params.put("pageSize", pageSize);
            List<Message> messages = messageService.getUserMessagesByPage(params);
            int total = messageService.getMessagesCount();
            result.put("success", true);
            result.put("data", messages);
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 分页获取用户发送的消息
     */
    @GetMapping("/page/user/sent")
    public Map<String, Object> getUserSentMessagesByPage(@RequestParam Long userId, @RequestParam int page, @RequestParam int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("offset", (page - 1) * pageSize);
            params.put("pageSize", pageSize);
            List<Message> messages = messageService.getUserSentMessagesByPage(params);
            int total = messageService.getMessagesCount();
            result.put("success", true);
            result.put("data", messages);
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 分页获取消息
     */
    @GetMapping("/page")
    public Map<String, Object> getMessagesByPage(@RequestParam int page, @RequestParam int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("offset", (page - 1) * pageSize);
            params.put("pageSize", pageSize);
            List<Message> messages = messageService.getMessagesByPage(params);
            int total = messageService.getMessagesCount();
            result.put("success", true);
            result.put("data", messages);
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
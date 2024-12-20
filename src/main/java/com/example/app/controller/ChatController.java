package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.app.domain.Chat;
import com.example.app.domain.Users;
import com.example.app.service.ChatService;
import com.example.app.service.NotificationService;
import com.example.app.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UsersService usersService;
    
    @Autowired
  	private NotificationService notificationService;

    // チャット画面にアクセスしたときの処理
    @GetMapping("/GameHive/Chat")
    public String chat(HttpSession session, Model model) {
        Integer loggedInUserId = (Integer) session.getAttribute("userId");

        if (loggedInUserId == null) {
            return "redirect:/GameHive/login";
        }

        // ログインユーザーの情報を取得
        Users loggedInUser = usersService.findById(loggedInUserId);
        if (loggedInUser == null) {
            return "error/404";
        }
        // チャット履歴を取得（仮のデータ）
        List<Chat> chatHistory = chatService.getAllChatMessages();
        model.addAttribute("chatHistory", chatHistory);
        
     // 未読通知数を取得
        int unreadNotificationsCount = notificationService.getUnreadNotifications(loggedInUserId.longValue()).size();

        // ThymeleafにログインユーザーのuserIdを渡す
        model.addAttribute("user", loggedInUser);
        model.addAttribute("userId", loggedInUserId);
        model.addAttribute("username", loggedInUser.getUsername());
        model.addAttribute("profileImageUrl", loggedInUser.getProfileImageName());
        model.addAttribute("unreadNotificationsCount", unreadNotificationsCount);

        return "chat";
    }

    // チャットメッセージを取得するAPI（初期メッセージの表示用）
    @GetMapping("/api/chat/messages")
    public List<Chat> getChatMessages() {
        return chatService.getAllChatMessages();
    }

    // WebSocket で受け取ったメッセージを全クライアントに送信
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/messages")
    public Chat sendChatMessage(Chat chat, @Header("simpSessionId") String sessionId, SimpMessageHeaderAccessor headerAccessor) {
        // WebSocketセッションからセッション属性を取得
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        Integer userId = (Integer) headerAccessor.getSessionAttributes().get("userId");

        // セッション情報がnullの場合のエラーハンドリング
        if (username == null || userId == null) {
            throw new IllegalStateException("Session information is missing");
        }

        // ユーザー情報をチャットメッセージにセット
        chat.setUserId(userId);
        chat.setUsername(username);

        // ユーザーのプロフィール画像を取得
        Users user = usersService.findById(userId);
        if (user != null) {
            chat.setProfileImageUrl(user.getProfileImageName());
        }

        // メッセージを送信（データベースに保存する例）
        chatService.sendChatMessage(chat);

        return chat;
    }

}

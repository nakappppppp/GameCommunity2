package com.example.app.websocket;

import java.time.LocalDateTime;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.app.domain.Chats;
import com.example.app.domain.Users;
import com.example.app.service.ChatService;
import com.example.app.service.UsersService;

@Service  // @Component は不要
public class ChatWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final UsersService userService;  // UserServiceを追加してユーザー情報を取得

    public ChatWebSocketHandler(SimpMessagingTemplate messagingTemplate, ChatService chatService, UsersService userService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
        this.userService = userService;
    }

    // メッセージ送信処理
    public void sendMessage(String message, Integer id) {
        // ユーザー情報をデータベースから取得
        Users user = userService.findById(id);
        if (user == null) {
           System.out.println("error");
            return;
        }

        // メッセージをデータベースに保存
        Chats chat = new Chats();
        chat.setUser(user);  // ユーザーオブジェクトをセットaaa
        chat.setContent(message);
        chat.setCreatedAt(LocalDateTime.now());
        chat.setUpdatedAt(LocalDateTime.now());

        try {
            chatService.sendChat(chat);  // メッセージをデータベースに保存
        } catch (Exception e) {
            // データベース保存時のエラーハンドリング
            // ログに記録する等
            return;
        }

        // メッセージをWebSocketで送信
        messagingTemplate.convertAndSend("/topic/messages", chat);
    }
}

package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.app.domain.Chat;
import com.example.app.domain.Users;
import com.example.app.service.ChatService;
import com.example.app.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;
    
    @Autowired
    private UsersService usersService;

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

      // ThymeleafにログインユーザーのuserIdを渡す
      model.addAttribute("userId", loggedInUserId);
      model.addAttribute("username", loggedInUser.getUsername());
      model.addAttribute("profileImageUrl", loggedInUser.getProfileImageName());

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
    public Chat sendChatMessage(Chat chat) {
        chatService.sendChatMessage(chat);
        return chat; // クライアントに送信されるメッセージ
    }
}


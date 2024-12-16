//package com.example.app.controller;
//
//import java.util.List;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.example.app.domain.Chats;
//import com.example.app.service.ChatService;
//
//
//@Controller
//@RequestMapping("/GameHive")
//public class ChatController {
//
//    private final ChatService chatService;
//
//    
//    public ChatController(ChatService chatService) {
//        this.chatService = chatService;
//    }
//
//    // チャットメッセージを送信
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/messages")
//    public Chats sendMessage(Chats chat) {
//        chatService.sendChat(chat);
//        return chat;  // 送信したメッセージをクライアントに送る
//    }
//
//    // チャットメッセージを全て取得
//    public List<Chats> getAllChats() {
//        return chatService.getAllChats();
//    }
//    
//    @GetMapping("/Chat")
//    public String Chat() {
//        return "chat";
//    }
//    
//    
//}

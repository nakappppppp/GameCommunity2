package com.example.app.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.app.domain.Chats;
import com.example.app.websocket.ChatWebSocketHandler;

@Controller
public class ChatWebSocketController {

    private final ChatWebSocketHandler chatWebSocketHandler;

    public ChatWebSocketController(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Chats sendChatMessage(Chats chat) {
        chatWebSocketHandler.sendMessage(chat.getContent(), chat.getUser().getId());
        return chat;
    }
}

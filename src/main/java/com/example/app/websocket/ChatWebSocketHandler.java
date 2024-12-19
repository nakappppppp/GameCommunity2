package com.example.app.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // クライアントから受け取ったメッセージを処理
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        // 受け取ったメッセージを全てのクライアントに送信
        session.sendMessage(new TextMessage("Server: " + payload));
    }
}

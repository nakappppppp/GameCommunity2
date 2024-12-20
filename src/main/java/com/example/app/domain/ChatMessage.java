package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ChatMessage {

    private Integer userId;         // ユーザーID
    private String userName;     // ユーザー名
    private String iconUrl;      // ユーザーアイコンURL
    private String content;      // メッセージ内容
    private String imageUrl;     // 添付された画像URL（任意）
    private LocalDateTime createdAt;  // 作成日時
    private LocalDateTime updatedAt;  // 更新日時

    // コンストラクタ
    public ChatMessage(Integer userId, String userName, String iconUrl, String content, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.userName = userName;
        this.iconUrl = iconUrl;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

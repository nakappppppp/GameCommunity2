package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Chat {
    private Long id;             // チャットメッセージのID
    private Integer userId;         // ユーザーID
    private String username;     // ユーザー名（追加）
    private String profileImageUrl; // ユーザーのアイコンURL（追加）
    private String content;      // チャットメッセージの内容
    private String imageUrl;     // 添付された画像のURL（任意）
    private LocalDateTime createdAt;  // 作成日時
    private LocalDateTime updatedAt;  // 更新日時
}


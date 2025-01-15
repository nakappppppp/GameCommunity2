package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Notification {
    private Long id; // 通知ID
    private Long userId; // 通知を受け取るユーザーID
    private Long followerId; // フォローしたユーザーのID
    private String content; // 通知内容（例: "ユーザーXがフォローしました"）
    private boolean isRead; // 通知が読まれたかどうか
    private LocalDateTime createdAt; // 通知が生成された日時

    // コンストラクタ
    public Notification(Long followedId, Long followerId,  String content) {
        this.userId = followedId;
        this.followerId = followerId;
        this.content = content;
        this.isRead = false;
        this.createdAt = LocalDateTime.now();
    }
}

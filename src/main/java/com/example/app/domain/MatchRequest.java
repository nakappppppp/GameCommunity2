package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MatchRequest {
    private Integer id;              // 対戦募集ID
    private Integer userId;          // 募集したユーザーID
    private String userName;         // ユーザー名
    private String gameGenreName;    // ゲームジャンル名
    private Integer gameGenreId; // ゲームジャンルID
    private LocalDateTime requestedAt; // 募集開始日時
    private String status;           // 募集のステータス（例: '募集中'、'終了'）
}

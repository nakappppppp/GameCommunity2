package com.example.app.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Chats {
  
    private Integer id; // チャットメッセージのID

    private Users user; // チャットメッセージを送信したユーザー

    private List<Users> recipients; // チャットメッセージを受信するユーザーリスト（宛先）

    private String content; // チャットの内容

    private String imageUrl; // 添付された画像のURL（任意）

    private LocalDateTime createdAt; // 作成日時

    private LocalDateTime updatedAt; // 更新日時
}

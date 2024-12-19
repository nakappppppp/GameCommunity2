package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Chat;

@Mapper
public interface ChatMapper {

    // チャットメッセージを挿入
    void insertChatMessage(Chat chat);

    // チャットメッセージを全て取得
    List<Chat> getAllChatMessages();

    // チャットメッセージを更新
    void updateChatMessage(Chat chat);
}

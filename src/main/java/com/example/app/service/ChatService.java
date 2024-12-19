package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.Chat;
import com.example.app.domain.Users;
import com.example.app.mapper.ChatMapper;
import com.example.app.mapper.UsersMapper;

@Service
public class ChatService {

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private UsersMapper usersMapper; // UsersMapperを注入

    // チャットメッセージの取得
    public List<Chat> getAllChatMessages() {
        List<Chat> chatMessages = chatMapper.getAllChatMessages();
        for (Chat chat : chatMessages) {
            // ユーザー情報を取得してチャットメッセージに追加
            Users user = usersMapper.findById(chat.getUserId().intValue()); // userIdを元にユーザーを検索
            chat.setUsername(user.getUsername()); // ユーザー名をセット
            chat.setProfileImageUrl(user.getProfileImageName()); // アイコン画像をセット
        }
        return chatMessages;
    }

    // チャットメッセージの送信
    public void sendChatMessage(Chat chat) {
        // userIdがnullの場合は例外をスロー
        if (chat.getUserId() == null) {
            throw new IllegalArgumentException("UserId is required");
        }

        // ユーザー情報を取得
        Users user = usersMapper.findById(chat.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // ユーザー名とプロフィール画像を設定
        chat.setUsername(user.getUsername());
        chat.setProfileImageUrl(user.getProfileImageName());

        // チャットメッセージをデータベースに挿入
        chatMapper.insertChatMessage(chat);
    }

    // チャットメッセージの更新
    public void updateChatMessage(Chat chat) {
        chatMapper.updateChatMessage(chat);
    }
}

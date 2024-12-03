package com.example.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Chats;
import com.example.app.mapper.ChatMapper;

@Service
public class ChatService {

	private final ChatMapper chatMapper;

  public ChatService(ChatMapper chatMapper) {
      this.chatMapper = chatMapper;
  }

  // すべてのチャットメッセージを取得
  public List<Chats> getAllChats() {
      return chatMapper.selectAllChats();
  }

  // チャットメッセージを送信
  @Transactional
  public void sendChat(Chats chat) {
      chat.setCreatedAt(LocalDateTime.now());
      chat.setUpdatedAt(LocalDateTime.now());
      chatMapper.insertChat(chat);
  }

  // チャットメッセージを更新
  @Transactional
  public void updateChat(Chats chat) {
      chat.setUpdatedAt(LocalDateTime.now());
      chatMapper.updateChat(chat);
  }

  // チャットメッセージを削除
  @Transactional
  public void deleteChat(Integer id) {
      chatMapper.deleteChat(id);
  }
}

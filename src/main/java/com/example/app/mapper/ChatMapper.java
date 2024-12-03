package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Chats;

@Mapper
public interface ChatMapper {

	 // チャットメッセージを全て取得
  List<Chats> selectAllChats();

  // 新しいチャットメッセージを挿入
  void insertChat(Chats chat);

  // チャットメッセージを更新
  void updateChat(Chats chat);

  // チャットメッセージを削除
  void deleteChat(Integer id); 
  
}

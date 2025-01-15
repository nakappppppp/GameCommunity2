package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.DirectMessage;

@Mapper
public interface DirectMessageMapper {
	
	// DMメッセージを保存する
    void insertDirectMessage(DirectMessage directMessage);

    // ユーザーの受信したメッセージを取得
    List<DirectMessage> getMessagesByUser(Integer recipientId);

    // 送信者と受信者の間でのメッセージを取得
    List<DirectMessage> getMessagesBetweenUsers(Integer senderId, Integer recipientId);

}

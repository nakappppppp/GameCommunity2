package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Users;

@Mapper
public interface UsersMapper {

//ユーザー名でユーザーを取得するメソッド
 Users findByUsername(String username);

 // 会員登録用メソッド
 void registerUser(Users user);
}

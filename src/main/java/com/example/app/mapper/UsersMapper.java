package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Users;

@Mapper
public interface UsersMapper {

//ユーザー名でユーザーを取得するメソッド
 Users findByUsername(String username);
 
 //ユーザーIDでユーザー情報を取得するメソッド
 Users findById(Integer id);
 
 //メールアドレスが重複しないように
 Users existsByEmail(String email);
 
 // ユーザー名で検索
 List<Users> searchUsersByUsername(String username);

 // 会員登録用メソッド
 void registerUser(Users user);
 
//プロフィール画像を更新するメソッド
// void updateProfileImage(Users user);

void deleteProfileImage(Integer userId);

void updateUserProfile(Users user);

// 複数のIDに基づいてユーザー情報を取得
List<Users> findByIds(List<Long> userIds);


}

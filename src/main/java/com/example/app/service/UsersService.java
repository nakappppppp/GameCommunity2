package com.example.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.app.domain.Users;
import com.example.app.mapper.UsersMapper;

@Service
public class UsersService {

	@Autowired
  private UsersMapper usersMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // ユーザー名で既存ユーザーを確認するメソッド
  public Users findUserByUsername(String username) {
      return usersMapper.findByUsername(username);
  }

  // 会員登録用メソッド
  public void registerUser(Users user) {
      // パスワードをハッシュ化
      String hashedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(hashedPassword);
      
      // firstName と lastName が未設定の場合にデフォルト値を設定
      if (user.getFirstName() == null) user.setFirstName("");  // 空文字を設定
      if (user.getLastName() == null) user.setLastName("");  // 空文字を設定
      if (user.getProfile_image() == null) user.setProfile_image("");
      if (user.getBio() == null) user.setBio("");
      if (user.getStatus() == null) user.setStatus(1);  // デフォルト値として "1" を設定
      if (user.getRole() == null) user.setRole("user");  // デフォルト値として "user" を設定
      if (user.getEmailVerifiedAt() == null) user.setEmailVerifiedAt(LocalDateTime.now());

      // ユーザーを登録
      usersMapper.registerUser(user);
  }
}

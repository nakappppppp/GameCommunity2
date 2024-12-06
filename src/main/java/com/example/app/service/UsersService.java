package com.example.app.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.Users;
import com.example.app.mapper.UsersMapper;

@Service
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;

    // ユーザー名でユーザーを検索
    public Users findUserByUsername(String username) {
        return usersMapper.findByUsername(username);
    }
    
    // ユーザーIDでユーザーを検索
    public Users findById(Integer id) {
        return usersMapper.findById(id);
    }

    // ユーザーの登録処理
    public void registerUser(Users user) {
        // パスワードのハッシュ化処理（BCryptを使用）
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        usersMapper.registerUser(user);
    }
    
    // ユーザー情報の更新（名前、メール、自己紹介、プロフィール画像を一括更新）
    public void updateUserProfile(Users user) {
        usersMapper.updateUserProfile(user);
    }

    // パスワードをBCryptでハッシュ化
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    // パスワードの照合
    public boolean checkPassword(String username, String password) {
        Users user = usersMapper.findByUsername(username);
        
        if (user == null) {
            return false;
        }
        
        // 入力されたパスワードと保存されたハッシュ化パスワードを照合
        return BCrypt.checkpw(password, user.getPassword());
    }

		
}

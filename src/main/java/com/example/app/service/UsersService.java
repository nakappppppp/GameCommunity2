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

    public Users findUserByUsername(String username) {
        return usersMapper.findByUsername(username);
    }

    public void registerUser(Users user) {
        // パスワードのハッシュ化処理（BCryptを使用）
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        usersMapper.registerUser(user);
    }

    private String hashPassword(String password) {
        // パスワードをBCryptでハッシュ化
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}

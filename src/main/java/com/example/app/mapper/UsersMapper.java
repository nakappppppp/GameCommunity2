package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.app.domain.Users;

@Mapper
public interface UsersMapper {

    // ユーザー名でユーザーを取得するメソッド
    @Select("SELECT * FROM users WHERE username = #{username}")
    Users findByUsername(String username);
}

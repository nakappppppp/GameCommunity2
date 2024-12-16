package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Follow;

@Mapper
public interface FollowMapper {

    // ユーザーが他のユーザーをフォローしているか確認
    int isFollowing(@Param("followerId") Long followerId, @Param("followedId") Long followedId);

    // ユーザーが他のユーザーをフォローする
    void insertFollow(Follow follow);

    // ユーザーが他のユーザーのフォローを解除する
    void deleteFollow(Follow follow);

    // フォロワー数を取得
    int getFollowerCount(@Param("followedId") Long followedId);

    // フォロー数を取得
    int getFollowingCount(@Param("followerId") Long followerId);
    
    // ユーザーがフォローしているユーザーのIDリストを取得
    List<Long> getFollowingUserIds(Long userId);

    // ユーザーをフォローしているユーザーのIDリストを取得
    List<Long> getFollowerUserIds(Long userId);
}

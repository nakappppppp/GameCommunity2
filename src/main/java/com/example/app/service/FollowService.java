package com.example.app.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.Follow;
import com.example.app.domain.Users;
import com.example.app.mapper.FollowMapper;

@Service
public class FollowService {

    @Autowired
    private FollowMapper followMapper;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private NotificationService notificationService;

    // ユーザーが他のユーザーをフォローしているか確認
    public boolean isFollowing(Long followerId, Long followedId) {
        return followMapper.isFollowing(followerId, followedId) > 0;
    }

    // ユーザーが他のユーザーをフォローする
    public void follow(Long followerId, Long followedId) {
        Follow follow = new Follow(followerId, followedId);
        if (!isFollowing(followerId, followedId)) {
            followMapper.insertFollow(follow);

            // フォローされたユーザーに通知を送る前に重複チェックをする
            if (!notificationService.notificationAlreadyExists(followedId, followerId)) {
                // フォロー通知を作成
                Users followerUser = usersService.findById(followerId.intValue());
                if (followerUser != null) {
                    String notificationContent = followerUser.getUsername();

                    // 修正: followerId と followedId を渡して通知を作成
                    notificationService.createNotification(followedId, followerId, notificationContent);
                }
            }
        }
    }

    // ユーザーが他のユーザーのフォローを解除する
    public void unfollow(Long followerId, Long followedId) {
        Follow follow = new Follow(followerId, followedId);
        if (isFollowing(followerId, followedId)) {
            followMapper.deleteFollow(follow);
            
            // ここでも呼び出し前にログを追加
            System.out.println("Unfollow initiated for follower: " + followerId + " and followed: " + followedId);
            
            notificationService.deleteFollowNotification(followerId, followedId);
        }
    }
    
    // フォローしているユーザーのリストを取得
    public List<Users> getFollowingUsers(Long userId) {
        // フォローしているユーザーのIDを取得
        List<Long> followingUserIds = followMapper.getFollowingUserIds(userId);

        // ユーザーIDリストからユーザー情報を取得
        if (followingUserIds.isEmpty()) {
            return Collections.emptyList();  // フォローしているユーザーがいない場合は空のリストを返す
        }

        List<Users> followingUsers = usersService.findByIds(followingUserIds);
        return followingUsers;
    }

    // フォロワーのリストを取得
    public List<Users> getFollowerUsers(Long userId) {
        // フォロワーのユーザーIDを取得
        List<Long> followerUserIds = followMapper.getFollowerUserIds(userId);

        // ユーザーIDリストからユーザー情報を取得
        if (followerUserIds.isEmpty()) {
            return Collections.emptyList();  // フォロワーがいない場合は空のリストを返す
        }

        List<Users> followerUsers = usersService.findByIds(followerUserIds);
        return followerUsers;
    }

    // フォロワー数を取得
    public int getFollowerCount(Long followedId) {
        return followMapper.getFollowerCount(followedId);
    }

    // フォロー数を取得
    public int getFollowingCount(Long followerId) {
        return followMapper.getFollowingCount(followerId);
    }
}

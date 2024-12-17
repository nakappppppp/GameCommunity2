package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.Notification;
import com.example.app.domain.Users;
import com.example.app.mapper.NotificationMapper;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    
    @Autowired
    private UsersService usersService;
    
   

    // 通知を挿入
    public void createNotification(Notification notification) {
        notificationMapper.insertNotification(notification);
    }
    
    // フォロー通知を削除（アンフォロー時）
    public void deleteFollowNotification(Long followerId, Long followedId) {
      // フォロワーのユーザー名を取得
      Users followerUser = usersService.findById(followerId.intValue());  // followerId からユーザー情報を取得
      String followerUsername = followerUser != null ? followerUser.getUsername() : "";  // ユーザー名を取得

      // 正しくパラメータが渡されているかをログで確認
      System.out.println("Deleting notification for follower: " + followerUsername + " and followed: " + followedId);

      // 通知を削除
      notificationMapper.deleteFollowNotification(followerUsername, followedId);  // Mapではなく個別に渡す
    }


    // ユーザーの未読通知を取得
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationMapper.getUnreadNotifications(userId);
    }

    // 通知を一括で既読にする
    public void markAllNotificationsAsRead(Long userId) {
        notificationMapper.markAllNotificationsAsRead(userId);  // Mapperで一括更新を実行
    }

    public void markNotificationsAsRead(Long notificationId) {
      notificationMapper.markAllNotificationsAsRead(notificationId);  // 一括更新を実行
  }
    
 // 通知がすでに存在するかを確認する
    public boolean notificationAlreadyExists(Long followedId, Long followerId) {
        // 通知内容が「フォローされました」かどうかを確認する
      List<Notification> existingNotifications = notificationMapper.getUnreadNotifications(followedId);

      // 通知の内容にフォロワーのIDが含まれているか確認
      for (Notification notification : existingNotifications) {
          if (notification.getContent().contains(followerId.toString())) {
              return true; // すでに通知が存在する場合
          }
      }
      return false; // 通知が存在しない場合
  }

    
}







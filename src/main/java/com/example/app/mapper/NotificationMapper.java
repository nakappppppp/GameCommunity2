package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Notification;

@Mapper
public interface NotificationMapper {

    // 通知を挿入
    void insertNotification(Notification notification);

    // ユーザーの未読通知を取得
    List<Notification> getUnreadNotifications(Long userId);

    // 通知を既読に更新
     void markAllNotificationsAsRead(Long userId);
     
     // フォロー通知を削除（アンフォロー時）
     void deleteFollowNotification(@Param("followerUsername") String followerUsername, @Param("followedId") Long followedId);

}

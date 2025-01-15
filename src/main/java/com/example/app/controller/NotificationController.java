package com.example.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.app.domain.Notification;
import com.example.app.service.NotificationService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/GameHive")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	// 通知ページを表示
	@GetMapping("/Notifications")
	public String notifications(HttpSession session, Model model) {
		Integer loggedInUserId = (Integer) session.getAttribute("userId");

		if (loggedInUserId == null) {
			return "redirect:/GameHive/login"; // ログインしていない場合
		}

		// ログインユーザーの未読通知を取得
		List<Notification> unreadNotifications = notificationService.getUnreadNotifications(loggedInUserId.longValue());

		// モデルに未読通知を追加
		model.addAttribute("unreadNotifications", unreadNotifications);
		
		 // ここで followerId をモデルに追加
        for (Notification notification : unreadNotifications) {
            Long followerId = notification.getFollowerId();  // followerIdを取得
            model.addAttribute("followerId", followerId);  // Modelに追加
        }

		// 通知を既読にする処理
		// 通知ページに遷移したときに未読通知を既読に更新
		notificationService.markAllNotificationsAsRead(loggedInUserId.longValue());

		// 未読通知数を0に更新（アイコンの通知数を0にする）
		model.addAttribute("unreadNotificationsCount", 0);

		return "notifications"; // 通知ページを表示
	}

	// 未読通知数を返す
	@GetMapping("/Notifications/count")
	@ResponseBody
	public Map<String, Integer> getNotificationCount(HttpSession session) {
		Integer loggedInUserId = (Integer) session.getAttribute("userId");

		// ログインしていない場合、またはセッションにuserIdがない場合
		if (loggedInUserId == null) {
			return Map.of("count", 0); // 未読通知数が0
		}

		try {
			// 未読通知数を取得
			List<Notification> unreadNotifications = notificationService.getUnreadNotifications(loggedInUserId.longValue());
			int unreadNotificationsCount = unreadNotifications.size();

			return Map.of("count", unreadNotificationsCount); // 未読通知数を返す
		} catch (Exception e) {
			// エラーが発生した場合（例えば、DB接続エラー等）
			e.printStackTrace(); // エラーログを出力
			return Map.of("count", 0); // エラー発生時は0を返す
		}
	}
}

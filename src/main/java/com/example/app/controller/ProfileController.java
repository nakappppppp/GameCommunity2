package com.example.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.Users;
import com.example.app.service.FollowService;
import com.example.app.service.NotificationService;
import com.example.app.service.UsersService;
import com.example.app.validation.ProfileGroup;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/GameHive")
public class ProfileController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private FollowService followService;  // FollowServiceをインジェクト
	
	@Autowired
	private NotificationService notificationService;

	// プロフィールページ表示
	@GetMapping("/Profile/{userId}")
	public String profile(@PathVariable Integer userId, HttpSession session, Model model) {
		Integer loggedInUserId = (Integer) session.getAttribute("userId");

		if (loggedInUserId == null) {
			// ユーザーがログインしていない場合の処理
			return "redirect:/GameHive/login"; // ログインページにリダイレクト
		}

		// ログインユーザーの情報を取得
		Users loggedInUser = usersService.findById(loggedInUserId);
		if (loggedInUser == null) {
			return "error/404"; // ユーザーが存在しない場合
		}

		// プロフィールページ用に、検索したユーザーの情報も取得
		Users user = usersService.findById(userId);
		if (user == null) {
			return "error/404"; // 指定されたユーザーが存在しない場合
		}
		
		 // フォロワー数とフォロー数を取得
    int followerCount = followService.getFollowerCount(userId.longValue());
    int followingCount = followService.getFollowingCount(userId.longValue());
    
    // 未読通知数を取得
    int unreadNotificationsCount = notificationService.getUnreadNotifications(loggedInUserId.longValue()).size();

		// モデルにログイン中のユーザー情報と、表示するプロフィール情報を追加
		model.addAttribute("loggedInUser", loggedInUser); // ログインユーザー情報を追加
		model.addAttribute("user", user); // 検索したユーザー情報を追加
		model.addAttribute("followerCount", followerCount); // フォロワー数を追加
    model.addAttribute("followingCount", followingCount); // フォロー数を追加
    model.addAttribute("unreadNotificationsCount", unreadNotificationsCount);

		// フォロー状態の確認（ログインユーザーがこのユーザーをフォローしているか）
		boolean isFollowing = followService.isFollowing(loggedInUserId.longValue(), userId.longValue());
		model.addAttribute("isFollowing", isFollowing); // フォロー状態を渡す

		return "profile"; // プロフィールページを表示
	}

	// プロフィール編集ページ表示
	@GetMapping("/EditProfile")
	public String editProfile(HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");

		if (userId == null) {
			// ユーザーがログインしていない場合の処理
			return "redirect:/GameHive/login"; // ログインページにリダイレクト
		}

		// ログインユーザーの情報を取得
		Users user = usersService.findById(userId);
		if (user == null) {
			return "error/404"; // ユーザーが存在しない場合
		}

		model.addAttribute("user", user); // ユーザー情報をビューに渡す

		return "editProfile"; // プロフィール編集ページを表示
	}

	// プロフィール情報と画像の更新処理
	@PostMapping("EditProfile")
	public String updateProfile(@Validated(ProfileGroup.class) @ModelAttribute("user") Users user,
			Errors errors,
			@RequestParam("originalImageName") String originalImageName,
			@RequestParam(value = "changedImageName", required = false) String changedImageName,
			@RequestParam(value = "profileImage", required = false) MultipartFile file, Model model) {

		// 画像がアップロードされなかった場合、以前の画像パスを保持
		if (file == null || file.isEmpty()) {
			user.setProfileImageName(originalImageName); // 既存の画像パスを保持
		}else {
			user.setProfileImageName(changedImageName);
		}

		// プロフィール画像のバリデーション
		if (file != null && !file.isEmpty()) {
			if (file.getSize() > 1048576) { // 画像のサイズが1MBを超える場合
				errors.rejectValue("profileImage", "error.profileImage", "画像のサイズは1MB以内である必要があります");
			} else if (!file.getContentType().startsWith("image/") && file.getSize() > 1) { // 画像ファイルの形式が適切か
				errors.rejectValue("profileImage", "error.profileImage", "画像ファイルのみ選択できます");
			}
		}

		// 他のフォームフィールドのバリデーション
		if (errors.hasErrors()) {
			// エラーがあればフォームを再表示
			model.addAttribute("user", user); // ユーザー情報をビューに渡す
			return "editProfile"; // エラーがあればフォームを再表示
		}

		try {
			// ユーザー情報を更新
			// 画像のアップロードがあった場合の処理
			if (file != null && !file.isEmpty()) {
				// 保存先のディレクトリを指定
				String uploadDir = "C:/Users/zd2Q15/pleiades/workspace/GameCommunity/src/main/resources/static/images/";
				//String uploadDir = "/Applications/Eclipse_2023-12.app/Contents/workspace/GameCommunity2/src/main/resources/static/images/";
				File dir = new File(uploadDir);
				if (!dir.exists()) {
					dir.mkdirs(); // ディレクトリがない場合は作成
				}

				// ファイル名を生成して保存
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				File targetFile = new File(uploadDir + fileName);
				file.transferTo(targetFile); // ファイルを保存

				// 画像のパス（String型）を作成
				String imgPath = "/images/" + fileName;

				// 画像パスをユーザーのprofileImageNameにセット
				user.setProfileImageName(imgPath); // 画像のパスをString型で設定
			}

			// ユーザー情報をデータベースに保存
			usersService.updateUserProfile(user);

			// ユーザー情報をビューに渡す
			model.addAttribute("user", user);

		} catch (IOException e) {
			e.printStackTrace();
			return "error"; // エラーが発生した場合、エラーページを表示
		}

		return "redirect:/GameHive/profileEditingCompleted"; // プロフィール編集完了ページにリダイレクト
	}

	@GetMapping("/profileEditingCompleted")
	public String profileEditingCompleted() {
		return "profileEditingCompleted";
	}

//フォロー・アンフォローの処理
  @PostMapping("/toggleFollow")
  public String toggleFollow(@RequestParam("followedId") Long followedId, HttpSession session, Model model) {
      Integer loggedInUserId = (Integer) session.getAttribute("userId");

      if (loggedInUserId == null) {
          return "redirect:/GameHive/login"; // ログインしていない場合、ログインページにリダイレクト
      }

      // フォロー・アンフォローの切り替え
      if (followService.isFollowing(loggedInUserId.longValue(), followedId)) {
          // すでにフォローしている場合はアンフォロー
          followService.unfollow(loggedInUserId.longValue(), followedId);
      } else {
          // フォローしていない場合はフォロー
          followService.follow(loggedInUserId.longValue(), followedId);
      }

      // フォロー状態を更新して再表示
      model.addAttribute("isFollowing", followService.isFollowing(loggedInUserId.longValue(), followedId));

      // プロフィールページを再表示
      return "redirect:/GameHive/Profile/" + followedId;
  }
  
  // フォローしているユーザーの一覧
  @GetMapping("/following/{userId}")
  public String following(@PathVariable Long userId, HttpSession session,  Model model) {
     
  	Integer user = (Integer) session.getAttribute("userId");

		if (user == null) {
			// ユーザーがログインしていない場合の処理
			return "redirect:/GameHive/login"; // ログインページにリダイレクト
		}

		// ログインユーザーの情報を取得
		Users users = usersService.findById(user);
		if (users == null) {
			return "error/404"; // ユーザーが存在しない場合
		}

		model.addAttribute("user", users); // ユーザー情報をビューに渡す
  	
  	// フォローしているユーザーのリストを取得
      List<Users> followingUsers = followService.getFollowingUsers(userId);
      
      // モデルにユーザーリストを追加
      model.addAttribute("followingUsers", followingUsers);
      
    

      return "following"; // following.htmlに遷移
  }

  // フォロワーの一覧
  @GetMapping("/followers/{userId}")
  public String followers(@PathVariable Long userId,HttpSession session, Model model) {
  	
  	Integer user = (Integer) session.getAttribute("userId");

		if (user == null) {
			// ユーザーがログインしていない場合の処理
			return "redirect:/GameHive/login"; // ログインページにリダイレクト
		}

		// ログインユーザーの情報を取得
		Users users = usersService.findById(user);
		if (users == null) {
			return "error/404"; // ユーザーが存在しない場合
		}

		model.addAttribute("user", users); // ユーザー情報をビューに渡す
  	
      // フォロワーのリストを取得
      List<Users> followerUsers = followService.getFollowerUsers(userId);

      // モデルにユーザーリストを追加
      model.addAttribute("followerUsers", followerUsers);

      return "followers"; // followers.htmlに遷移
  }
	

	// プロフィール画像削除処理
	@PostMapping("/Profile/deletePicture")
	public String deleteProfilePicture(@RequestParam("userId") Integer userId) {
		try {
			// ユーザーのプロフィール画像を削除
			Users user = usersService.findById(userId);
			user.setProfileImage(null); // 画像パスを削除
			usersService.updateUserProfile(user);

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // エラーが発生した場合、エラーページを表示
		}

		return "redirect:/GameHive/Profile"; // プロフィールページにリダイレクト
	}
}

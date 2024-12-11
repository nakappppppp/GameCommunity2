package com.example.app.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.Users;
import com.example.app.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/GameHive")
public class ProfileController {

    @Autowired
    private UsersService usersService;

    // プロフィールページ表示
    @GetMapping("/Profile/{userId}")
    public String profile(@PathVariable Integer userId, HttpSession session, Model model) {
        Integer loggedInUserId = (Integer) session.getAttribute("userId");

        if (loggedInUserId == null) {
            // ユーザーがログインしていない場合の処理
            return "redirect:/GameHive/login";  // ログインページにリダイレクト
        }

        // ログインユーザーの情報を取得
        Users loggedInUser = usersService.findById(loggedInUserId);
        if (loggedInUser == null) {
            return "error/404";  // ユーザーが存在しない場合
        }

        // プロフィールページ用に、検索したユーザーの情報も取得
        Users user = usersService.findById(userId);
        if (user == null) {
            return "error/404";  // 指定されたユーザーが存在しない場合
        }

        // モデルにログイン中のユーザー情報と、表示するプロフィール情報を追加
        model.addAttribute("loggedInUser", loggedInUser);  // ログインユーザー情報を追加
        model.addAttribute("user", user);  // 検索したユーザー情報を追加

        return "profile";  // プロフィールページを表示
    }

    // プロフィール編集ページ表示
    @GetMapping("/EditProfile")
    public String editProfile(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            // ユーザーがログインしていない場合の処理
            return "redirect:/GameHive/login";  // ログインページにリダイレクト
        }

        // ログインユーザーの情報を取得
        Users user = usersService.findById(userId);
        if (user == null) {
            return "error/404";  // ユーザーが存在しない場合
        }

        model.addAttribute("user", user);  // ユーザー情報をビューに渡す

        return "editProfile";  // プロフィール編集ページを表示
    }

    // プロフィール情報と画像の更新処理
    @PostMapping("/Profile/update")
    public String updateProfile(@RequestParam("profileImage") MultipartFile file, 
                                @RequestParam("userId") Integer userId,
                                @RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("email") String email,
                                @RequestParam("bio") String bio) {
        try {
            // ユーザー情報を更新
            Users user = usersService.findById(userId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setBio(bio);

            // プロフィール画像の更新があれば処理
            if (!file.isEmpty()) {
                String uploadDir = "C:/Users/zd2Q15/pleiades/workspace/GameCommunity/src/main/resources/static/images/";
                //String uploadDir = "/Applications/Eclipse_2023-12.app/Contents/workspace/GameCommunity2/src/main/resources/static/images/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();  // ディレクトリが存在しない場合は作成
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                File targetFile = new File(uploadDir + fileName);

                file.transferTo(targetFile);

                String imgPath = "/images/" + fileName;
                user.setProfileImage(imgPath);
            }

            usersService.updateUserProfile(user);

        } catch (IOException e) {
            e.printStackTrace();
            return "error";  // エラーが発生した場合、エラーページを表示
        }

        return "redirect:/GameHive/profileEditingCompleted";  // プロフィールページにリダイレクト
    }

    @GetMapping("/profileEditingCompleted")
    public String profileEditingCompleted() {
        return "profileEditingCompleted";
    }

    // プロフィール画像削除処理
    @PostMapping("/Profile/deletePicture")
    public String deleteProfilePicture(@RequestParam("userId") Integer userId) {
        try {
            // ユーザーのプロフィール画像を削除
            Users user = usersService.findById(userId);
            user.setProfileImage(null);  // 画像パスを削除
            usersService.updateUserProfile(user);

        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // エラーが発生した場合、エラーページを表示
        }

        return "redirect:/GameHive/Profile"; // プロフィールページにリダイレクト
    }
}

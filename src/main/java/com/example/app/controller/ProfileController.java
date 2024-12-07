package com.example.app.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/Profile")
    public String profile(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            // ユーザーがログインしていない場合の処理
            return "redirect:/GameHive/login";  // ログインページにリダイレクト
        }

        // ユーザーIDを元にユーザー情報を取得
        Users user = usersService.findById(userId);
        model.addAttribute("user", user);  // ユーザー情報をビューに渡す

        return "profile";  // プロフィールページを表示
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
                // 保存先ディレクトリの絶対パス
//                String uploadDir = "C:/Users/zd2Q15/pleiades/workspace/GameCommunity/src/main/resources/static/images/";
                String uploadDir = "/Applications/Eclipse_2023-12.app/Contents/workspace/GameCommunity2/src/main/resources/static/images/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();  // ディレクトリが存在しない場合は作成
                }

                // 新しいファイル名
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                
                // ファイル保存先の絶対パス
                File targetFile = new File(uploadDir + fileName);

                // 画像ファイルを保存
                file.transferTo(targetFile);

                // Webアクセス用の相対パスを設定
                String imgPath = "/images/" + fileName;

                // プロフィール画像を更新
                user.setProfileImage(imgPath);
            }

            // ユーザー情報を更新
            usersService.updateUserProfile(user);  // ユーザー情報をデータベースに保存

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
            usersService.updateUserProfile(user);  // ユーザー情報を更新

        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // エラーが発生した場合、エラーページを表示
        }

        return "redirect:/GameHive/Profile"; // プロフィールページにリダイレクト
    }
}

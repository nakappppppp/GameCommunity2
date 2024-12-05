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
        System.out.println("user->"+user);//ok
        model.addAttribute("user", user);  // ユーザー情報をビューに渡す

        return "profile";  // プロフィールページを表示
    }


    // プロフィール画像の変更処理
//		@PostMapping("/Profile/updatePicture")
//		 public String updateProfilePicture(@RequestParam("profileImage") MultipartFile file, @RequestParam("userId") Integer userId ,
//				 Model model) {
//		     if (!file.isEmpty()) {
//		         try {
//		             // 保存先ディレクトリのパス
////		             String uploadDir = "./uploads/images/";
//           String uploadDir = "C:/Users/zd2Q15/pleiades/workspace/GameCommunity/uploads/images/";
////		        	 String uploadDir = "C:/Users/zd2Q15/pleiades/workspace/GameCommunity/src/main/resources/static/images/";
//		             File dir = new File(uploadDir);
//		             if (!dir.exists()) {
//		                 dir.mkdirs();  // ディレクトリが存在しない場合は作成
//		             }
//		
//		             // 新しいファイル名
//		             String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//	             String imgPath = uploadDir + fileName;
////		             String imgPath = "/images/" + fileName;
//		             //File targetFile = new File(uploadDir + fileName);
//		             File targetFile = new File(imgPath);
//		             System.out.println("targetFile->"+targetFile);
//		             file.transferTo(targetFile);
//		
//		             // ユーザー情報を取得してプロフィール画像を更新
//		             Users user = usersService.findById(userId);
//		             //user.setProfile_image(uploadDir + fileName);  // 保存先パスをセット
//		             user.setProfile_image(imgPath);  // 保存先パスをセット
//		             usersService.updateProfileImage(user);  // ユーザー情報を更新
//		             
//		             System.out.println("imgPath: " + imgPath);
//		             model.addAttribute("picture",imgPath);
//		             
//		
//		         } catch (IOException e) {
//		             e.printStackTrace();
//		             return "error"; // エラーが発生した場合、エラーページを表示
//		         }
//		     }
//		
////		       return"profile";
//		     return "redirect:/GameHive/Profile";  //プロフィールページにリダイレクト
//		 }
    
    @PostMapping("/Profile/updatePicture")
    public String updateProfilePicture(@RequestParam("profileImage") MultipartFile file, 
                                       @RequestParam("userId") Integer userId
                                       ) {
        if (!file.isEmpty()) {
            try {
                // 保存先ディレクトリの絶対パス
                String uploadDir = "C:/Users/zd2Q15/pleiades/workspace/GameCommunity/src/main/resources/static/images/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();  // ディレクトリが存在しない場合は作成
                }

                // 新しいファイル名
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                
                // ファイル保存先の絶対パス
                File targetFile = new File(uploadDir + fileName);  // 画像を保存する場所

                // 画像ファイルを保存
                file.transferTo(targetFile);

                // Webアクセス用の相対パスを設定（例: /images/filename）
                String imgPath = "/images/" + fileName;

                // ユーザー情報を取得してプロフィール画像を更新
                Users user = usersService.findById(userId);
                user.setProfileImage(imgPath);  // Webアクセス用のパスを設定
                usersService.updateProfileImage(user);  // ユーザー情報を更新

                // モデルに画像パスを設定
             

            } catch (IOException e) {
                e.printStackTrace();
                return "error";  // エラーが発生した場合、エラーページを表示
            }
        }

        return "redirect:/GameHive/Profile";  // プロフィールページにリダイレクト
    }

    // プロフィール画像削除処理
    @PostMapping("/Profile/deletePicture")
    public String deleteProfilePicture(@RequestParam("userId") Integer userId) {
        try {
            // ユーザーのプロフィール画像を削除
            usersService.deleteProfileImage(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // エラーが発生した場合、エラーページを表示
        }

        return "redirect:/GameHive/Profile"; // プロフィールページにリダイレクト
    }
}

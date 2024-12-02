package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Users;
import com.example.app.service.UsersService;

@Controller
@RequestMapping("/GameHive")
public class RegisterController {
    
    @Autowired
    private UsersService usersService;

    // 登録ページを表示
    @GetMapping("/register")
    public String registerUserForm() {
        return "register";
    }

    // 登録完了ページを表示
    @GetMapping("/successRegister")
    public String successRegister() {
        return "successRegister";
    }

    // ユーザー登録処理
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        // 既に同じユーザー名が存在するかチェック
        Users existingUser = usersService.findUserByUsername(username);
        if (existingUser != null) {
            // ユーザー名がすでに存在している場合、エラーメッセージとともに登録フォームに戻す
            return "redirect:/GameHive/register?error=username_taken";
        }

        // 新規ユーザーを作成
        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);  // パスワードのハッシュ化はUsersServiceで行う


        
        // ユーザーをデータベースに登録
        usersService.registerUser(user);

        // 登録が成功した場合、登録完了ページにリダイレクト
        return "redirect:/GameHive/successRegister";
    }
}

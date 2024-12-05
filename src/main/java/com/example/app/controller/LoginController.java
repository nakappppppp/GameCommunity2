package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.Users;
import com.example.app.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/GameHive")
public class LoginController {
    
    @Autowired
    private UsersService usersService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String checkUser(String username, String password, Model model, HttpSession session) {
        System.out.println("Received username: " + username + ", password: " + password);

        // ユーザー名とパスワードのチェック
        if (usersService.checkPassword(username, password)) {
            System.out.println("Login success");

            // ユーザー情報を取得
            Users user = usersService.findUserByUsername(username);

            // ユーザーが見つかった場合
            if (user != null) {
                // セッションにユーザー名とIDを保存
                session.setAttribute("username", username);
                session.setAttribute("userId", user.getId());

                model.addAttribute("message", "ログイン成功！");
                return "redirect:/GameHive/home";  // ホーム画面にリダイレクト
            }
        }

        // 認証失敗の場合
        System.out.println("Login error");
        model.addAttribute("error", "無効なユーザー名またはパスワード");
        return "FailedLogin";  // ログイン失敗ページに遷移
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/forms")
    public String forms() {
        return "forms";
    }
}

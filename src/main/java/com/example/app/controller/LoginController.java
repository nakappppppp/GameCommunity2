package com.example.app.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.Users;
import com.example.app.service.EmailService;
import com.example.app.service.RebuildPassService;
import com.example.app.service.UsersService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/GameHive")
public class LoginController {
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private RebuildPassService rebuildPassService;
    
    @Autowired
    private EmailService emailService;
    

    @GetMapping("/login")
    public String loginForm(Model model) {
    	model.addAttribute("users", new Users());
        return "login";
    }

    @PostMapping("/login")
    public String checkUser(String username, String password, 
    		Model model, HttpSession session,
    		@Valid @ModelAttribute("users") Users users,
    		Errors errors) {
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
        model.addAttribute("error", "ユーザーが見つかりませんでした");
        return "FailedLogin";  // ログイン失敗ページに遷移
    }
    
    @GetMapping("/RebuildPass")
	public String rebuildPass() {
	    return "rebuildPass";
	}
    
    @PostMapping("/RebuildPass")
    public String rebuildPass(String username, String email) {
        Users user = usersService.findUserByUsername(username);

        if (user != null && user.getEmail().equals(email)) {
            String token = UUID.randomUUID().toString();

            // トークンを保存
            rebuildPassService.saveRebuildPassToken(user.getId(), token);

            // パスワードリセットURL生成
            String resetUrl = "localhost:8080/GameHive/InputRebuildPass?token=" + token;

            // メール送信
            emailService.sendPasswordResetEmail(user.getEmail(), resetUrl);

            return "redirect:/GameHive/SendMail";
        }

        return "redirect:/GameHive/SendMailFailed";
    }
    
    @GetMapping("/InputRebuildPass")
	public String inputRebuildPass() {
		return "inputRebuildPass";
	}





    @GetMapping("/home")
    public String home(HttpSession session,Model model) {
    	
    	String username = (String) session.getAttribute("username");
      
      if (username != null) {
          Users user = usersService.findUserByUsername(username);
          if (user != null) {
              model.addAttribute("user", user);  // userオブジェクトをモデルに追加
          }
      }
        return "home";
    }

    @GetMapping("/forms")
    public String forms() {
        return "forms";
    }
}

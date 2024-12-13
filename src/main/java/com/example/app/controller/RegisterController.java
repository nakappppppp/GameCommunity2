package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.Users;
import com.example.app.service.UsersService;
import com.example.app.validation.RegisterGroup;

@Controller
@RequestMapping("/GameHive")
public class RegisterController {
    
    @Autowired
    private UsersService usersService;

    // 登録ページを表示
    @GetMapping("/register")
    public String registerUserForm(Model model) {
    	model.addAttribute("users", new Users());
        return "register";
    }

    // 登録完了ページを表示
    @GetMapping("/successRegister")
    public String successRegister() {
        return "successRegister";
    }

    // ユーザー登録処理
    @PostMapping("/register")
    public String registerUser(@Validated(RegisterGroup.class) @ModelAttribute("users") Users users, Errors errors) {
    	 // 既に同じユーザー名が存在するかチェック
      Users existingUser = usersService.findUserByUsername(users.getUsername());
      if (existingUser != null) {
          // ユーザー名がすでに存在している場合、エラーメッセージを追加
          errors.rejectValue("username", "username.taken", "すでにその名前のユーザーは存在しています");
      }
      
      // メールアドレスの重複エラーチェック
      if (usersService.existsByEmail(users.getEmail()) != null) {
          errors.rejectValue("email", "error.email", "このメールアドレスはすでに使用されています。");
      }

      // バリデーションエラーがあれば、フォームに戻る
      if (errors.hasErrors()) {
          return "register";  // バリデーションエラーがある場合、フォームに戻す
      }

      // ユーザー登録処理
      usersService.registerUser(users);
      
      // 登録成功時、登録完了ページへリダイレクト
      return "redirect:/GameHive/successRegister";
  }
}

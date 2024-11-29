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
public class LoginController {
	
	
	@Autowired
  private UsersService usersService;


	@GetMapping("/login")
	public String  showLoginForm() {
		return "login";
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
//	@PostMapping("/login")
//	public String processLogin(
//			@RequestParam ("userName") String userName,
//			@RequestParam ("password") String password,
//			Model model) {
//		//TODO: process POST request
//		
//		return entity;
//	}
	
	@GetMapping("/register")
	public String registerUser(){
		return "register";
	}
	
	
	@GetMapping("/successRegister")
	public String successRegister() {
		return "successRegister";
	}
	
	
	@PostMapping("/register")
	public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
    // 既存のユーザー名チェック
		System.out.println(username +  "," + email + "," + password);
    Users existingUser = usersService.findUserByUsername(username);
    if (existingUser != null) {
        return "redirect:/register?error=username_taken";  // ユーザー名が既に存在する場合
    }
        // 新規ユーザーを登録
        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);  // パスワードのハッシュ化はUsersService内で行う
        
        usersService.registerUser(user);
        
        return "redirect:/successRegister"; // 登録後、ログイン画面へリダイレクト
    
    }
	
	

}


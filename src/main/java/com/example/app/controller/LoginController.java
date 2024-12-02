package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.service.UsersService;



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
	 public String checkUser(
			 String username, String password, 
			 Model model
			 ) {
		  System.out.println("Received userId: " + username + ", password: " + password);
		 if(usersService.checkPassword(username,password)) {
			 System.out.println("success");
			 model.addAttribute("message","ログイン成功！");
			 return "gameHiveHome";
		 }else {
			 System.out.println("error");
			 model.addAttribute("error","無効なユーザー名またはパスワード");
			 return "login";
		 }
	 	//TODO: process POST request
	 	
	 	
	 }
	 
	 
}

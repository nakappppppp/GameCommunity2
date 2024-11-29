package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/GameHive")
public class LoginController {

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
	
	

}


package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;


@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // セッションを無効にする
        session.invalidate();
        // ログインページやホームページにリダイレクト
        return "redirect:/GameHive/login"; // または "/"
    }
}
package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Users;
import com.example.app.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller  // @RestControllerではなく@Controllerに変更
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    // ユーザー名で検索
    @GetMapping("/search/username")
    public List<Users> searchByUsername(@RequestParam String username) {
        List<Users> users = usersService.searchUsersByUsername(username);
        System.out.println("検索結果: " + users);  // デバッグ用にコンソールに出力
        return users; // List<Users>をそのまま返す
    }

    // 検索結果ページの表示（HTMLページを表示するためのエンドポイント）
    @GetMapping("/search-results")
    public String searchResultsPage(@RequestParam String username, Model model, HttpSession session) {
	String username2 = (String) session.getAttribute("username");
      
      if (username2 != null) {
          Users user2 = usersService.findUserByUsername(username2);
          if (user2 != null) {
              model.addAttribute("user2", user2);  // userオブジェクトをモデルに追加
          }
      }
        List<Users> users = usersService.searchUsersByUsername(username);
        model.addAttribute("users", users); // 検索結果をmodelに追加
        return "search-results"; // search-results.htmlを表示
    }
}


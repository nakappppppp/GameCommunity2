package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.GameGenre;
import com.example.app.service.GameGenreService;
import com.example.app.service.MatchRequestService;

@Controller
@RequestMapping("/GameHive")
public class MatchRequestController {

    @Autowired
    private GameGenreService gameGenreService;

    @Autowired
    private MatchRequestService matchRequestService;

    // ゲームジャンル選択画面の表示
    @GetMapping("/CreateMatchRequest")
    public String showCreateMatchRequestForm(Model model) {
        List<GameGenre> gameGenres = gameGenreService.getAllGameGenres();
        model.addAttribute("gameGenres", gameGenres);
        return "createMatchRequest";
    }

    // 対戦相手募集リクエストの作成
    @PostMapping("/createMatchRequest")
    public String createMatchRequest(Integer userId, Integer gameGenreId) {
        matchRequestService.createMatchRequest(userId, gameGenreId);
        return "redirect:/matchRequests";
    }
}
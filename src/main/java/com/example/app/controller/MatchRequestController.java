package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.GameGenre;
import com.example.app.domain.MatchRequest;
import com.example.app.domain.Users;
import com.example.app.service.GameGenreService;
import com.example.app.service.MatchRequestService;
import com.example.app.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/GameHive")
public class MatchRequestController {

    @Autowired
    private GameGenreService gameGenreService;

    @Autowired
    private MatchRequestService matchRequestService;
    
    @Autowired
    private UsersService usersService;

    // ゲームジャンル選択画面の表示
    @GetMapping("/CreateMatchRequest")
    public String showCreateMatchRequestForm(Model model, HttpSession session) {
        // セッションから userId を取得
        Integer userId = (Integer) session.getAttribute("userId");

        // ユーザーがログインしていない場合、ログインページにリダイレクト
        if (userId == null) {
            return "redirect:/login";
        }

        List<GameGenre> gameGenres = gameGenreService.getAllGameGenres();
        model.addAttribute("gameGenres", gameGenres);
        return "createMatchRequest";  // ゲームジャンル選択フォームを表示
    }

    // 対戦相手募集リクエストの作成
    @PostMapping("/CreateMatchRequest")
    public String createMatchRequest(Integer gameGenreId, HttpSession session) {
        // セッションから userId を取得
        Integer userId = (Integer) session.getAttribute("userId");

        // ユーザーがログインしていない場合、ログインページにリダイレクト
        if (userId == null) {
            return "redirect:/login";
        }

        // ゲームジャンル ID とユーザー ID を使って対戦リクエストを作成
        matchRequestService.createMatchRequest(userId, gameGenreId);

        return "redirect:/GameHive/matchRequests";  // 対戦リスト画面にリダイレクト
    }
    
    // 募集中の対戦リクエストを表示
    @GetMapping("/matchRequests")
    public String showAvailableMatchRequests(Model model) {
        // 募集中の対戦リクエストを取得
       List<MatchRequest> matchRequests = matchRequestService.getAvailableMatchRequests();
        // ユーザー名とゲームジャンル名を取得してリストにセット
        for (MatchRequest matchRequest : matchRequests) {
            // ユーザー情報を取得 例: ユーザーIDからユーザーを取得)
            Users user = usersService.findById(matchRequest.getUserId());
            matchRequest.setUserName(user != null ? user.getUsername() : "不明");

            // ゲームジャンル情報を取得 (例: ゲームジャンルIDからゲームジャンル名を取得)
            GameGenre gameGenre = gameGenreService.findById(matchRequest.getGameGenreId());
            matchRequest.setGameGenreName(gameGenre != null ? gameGenre.getName() : "不明");
        }

        // モデルに対戦リクエストを追加
        model.addAttribute("matchRequests", matchRequests);

        return "matchRequests";  // matchRequests.html を返す
    }
    
    
}

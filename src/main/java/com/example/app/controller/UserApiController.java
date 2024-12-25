package com.example.app.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class UserApiController {

    @GetMapping("/getUserId")
    public ResponseEntity<Map<String, Object>> getUserId(HttpSession session) {
        // セッションからユーザーIDを取得
        Object userId = session.getAttribute("userId");

        if (userId == null) {
            // ユーザーIDがセッションに保存されていない場合
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("error", "ユーザーIDが設定されていません"));
        }

        // ユーザーIDが存在する場合は返す
        return ResponseEntity.ok(Collections.singletonMap("userId", userId));
    }
}

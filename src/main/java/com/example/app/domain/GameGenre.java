package com.example.app.domain;

import lombok.Data;

@Data
public class GameGenre {
    private Integer id;             // ゲームジャンルID
    private String name;            // ゲームジャンル名
    private String description;     // ジャンルの説明
}

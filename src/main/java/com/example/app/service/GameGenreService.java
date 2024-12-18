package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.GameGenre;
import com.example.app.mapper.GameGenreMapper;

@Service
public class GameGenreService {

    @Autowired
    private GameGenreMapper gameGenreMapper;

    // 全てのゲームジャンルを取得
    public List<GameGenre> getAllGameGenres() {
        return gameGenreMapper.getAllGameGenres();
    }
}

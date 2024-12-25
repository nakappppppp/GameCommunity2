package com.example.app.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.GameGenre;

@Mapper
public interface GameGenreMapper {
    
    // ゲームジャンルを全て取得
    List<GameGenre> getAllGameGenres();
    
    GameGenre findById(Integer id);
}

package com.example.app.mapper;


import com.example.app.domain.GameGenre;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameGenreMapper {
    
    // ゲームジャンルを全て取得
    List<GameGenre> getAllGameGenres();
}

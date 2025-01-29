package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.RebuildPassToken;

@Mapper
public interface RebuildPassTokenMapper {

	void insertRebuildPassToken(RebuildPassToken token);

    RebuildPassToken findByToken(String token);
}


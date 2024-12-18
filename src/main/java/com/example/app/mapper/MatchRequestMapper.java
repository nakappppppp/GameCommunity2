package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.MatchRequest;

@Mapper
public interface MatchRequestMapper {
    
    // 募集中の対戦相手募集リクエストを作成
    void createMatchRequest(MatchRequest matchRequest);

    // 募集中の対戦相手情報を取得
    List<MatchRequest> getAvailableMatchRequests();
}

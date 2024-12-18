package com.example.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.MatchRequest;
import com.example.app.mapper.MatchRequestMapper;

@Service
public class MatchRequestService {

    @Autowired
    private MatchRequestMapper matchRequestMapper;

    // 対戦相手募集リクエストを作成
    public void createMatchRequest(Integer userId, Integer gameGenreId) {
        MatchRequest matchRequest = new MatchRequest();
        matchRequest.setUserId(userId);
        matchRequest.setGameGenreId(gameGenreId);
        matchRequest.setRequestedAt(LocalDateTime.now());
        matchRequest.setStatus("募集中");

        matchRequestMapper.createMatchRequest(matchRequest);
    }

    // 募集中の対戦相手情報を取得
    public List<MatchRequest> getAvailableMatchRequests() {
        return matchRequestMapper.getAvailableMatchRequests();
    }
}
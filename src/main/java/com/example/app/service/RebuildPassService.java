package com.example.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.RebuildPassToken;
import com.example.app.mapper.RebuildPassTokenMapper;

@Service
public class RebuildPassService {

	@Autowired
    private RebuildPassTokenMapper tokenMapper;

    public void saveRebuildPassToken(Integer integer, String token) {
        RebuildPassToken resetToken = new RebuildPassToken();
        resetToken.setUserId(integer);
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1)); // 1時間有効

        tokenMapper.insertRebuildPassToken(resetToken);
    }

    public boolean isTokenValid(String token) {
        RebuildPassToken resetToken = tokenMapper.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }
}

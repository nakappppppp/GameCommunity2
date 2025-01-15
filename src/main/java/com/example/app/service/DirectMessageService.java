package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.DirectMessage;
import com.example.app.mapper.DirectMessageMapper;

@Service
public class DirectMessageService {

	@Autowired
    private  DirectMessageMapper directMessageMapper;


    @Transactional
    public void sendDirectMessage(DirectMessage directMessage) {
        directMessageMapper.insertDirectMessage(directMessage);
    }

    public List<DirectMessage> getMessagesByUser(Integer recipientId) {
        return directMessageMapper.getMessagesByUser(recipientId);
    }

    public List<DirectMessage> getMessagesBetweenUsers(Integer senderId, Integer recipientId) {
        return directMessageMapper.getMessagesBetweenUsers(senderId, recipientId);
    }
}

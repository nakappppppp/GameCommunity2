package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.DirectMessage;
import com.example.app.service.DirectMessageService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/GameHive")
public class DirectMessageController {

	@Autowired
    private  DirectMessageService directMessageService;
	@Autowired
    private  SimpMessagingTemplate messagingTemplate;

	@GetMapping("/DM/{recipientId}")
	public String directMessagePage(@PathVariable Integer recipientId, Model model,HttpSession session) {
	    // 必要に応じてデータを取得し、モデルに追加
		Integer senderId = (Integer) session.getAttribute("userId");
		model.addAttribute("senderId", senderId);
	    model.addAttribute("recipientId", recipientId);
	    return "directMessage"; 
	}
    
	@PostMapping("/DM/info")
	public ResponseEntity<String> sendDirectMessage(@RequestBody DirectMessage directMessage) {
	    directMessageService.sendDirectMessage(directMessage);
	    messagingTemplate.convertAndSendToUser(
	            String.valueOf(directMessage.getRecipientId()), 
	            "/queue/messages", 
	            directMessage
	    );
	    return ResponseEntity.ok("Message sent successfully.");
	}


   

    @GetMapping("/{userId}")
    public ResponseEntity<List<DirectMessage>> getMessagesByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(directMessageService.getMessagesByUser(userId));
    }
}

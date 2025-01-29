package com.example.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String resetUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("パスワード再設定のご案内");
        message.setText("以下のリンクをクリックして、パスワードを再設定してください。\n" + resetUrl);
        mailSender.send(message);
    }
}

package com.example.app.domain;

import lombok.Data;

@Data
public class DirectMessage {

	private Integer id;
    private Integer senderId;
    private Integer recipientId;
    private String messageBody;
    private String sentAt;
}

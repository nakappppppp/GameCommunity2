package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RebuildPassToken {

	private Long id;
	private Integer userId;
	private String token;
	private LocalDateTime expiryDate;
}

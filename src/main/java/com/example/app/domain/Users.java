package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Users {

	private Integer id;
	private String username;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String profile_image;
	private String bio;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer status;
	private String role;
	private LocalDateTime emailVerifiedAt;
}





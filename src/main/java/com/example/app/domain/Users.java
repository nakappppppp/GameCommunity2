package com.example.app.domain;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.example.app.validation.ProfileGroup;
import com.example.app.validation.RegisterGroup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Users {

	private Integer id;
	
	 @NotEmpty(message = "ユーザー名を入力してください", groups = {RegisterGroup.class})
   @Size(min = 1, max = 10, message = "ユーザー名は1~10文字で入力してください", groups = {RegisterGroup.class})
   @Pattern(regexp = "^[A-Za-z0-9]*$", message = "ユーザー名は半角英数字で入力してください", groups = {RegisterGroup.class})
   private String username;

   @NotEmpty(message = "メールアドレスを入力してください", groups = {RegisterGroup.class, ProfileGroup.class})
   @Email(message = "メールアドレスが無効です", groups = {RegisterGroup.class, ProfileGroup.class})
   private String email;

  @NotEmpty(message = "パスワードを入力してください",groups = {RegisterGroup.class}) 
  @Size(min = 8, max = 12, message = "パスワードは8~12文字で入力してください", groups = {RegisterGroup.class})
  @Pattern(regexp = "^[A-Za-z0-9]*$", message = "パスワードは半角英数字で入力してください", groups = {RegisterGroup.class})
   private String password;
	
   @Size(max = 10, message = "登録できる文字数は10文字以内です", groups = {ProfileGroup.class})
	private String firstName;
   
   @Size(max = 10, message = "登録できる文字数は10文字以内です", groups = {ProfileGroup.class})
	private String lastName;
   
	private MultipartFile profileImage;
	private String profileImageName;
	
	@Size(max = 40, message = "登録できる文字数は40文字以内です", groups = {ProfileGroup.class})
	private String bio;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer status;
	private String role;
	private LocalDateTime emailVerifiedAt;
}





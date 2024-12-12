package com.example.app.domain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Users {

	private Integer id;
	
	 @NotEmpty(message = "ユーザー名を入力してください")
   @Size(min = 1, max = 10, message = "ユーザー名は1~10文字で入力してください")
   @Pattern(regexp = "^[A-Za-z0-9]*$", message = "ユーザー名は半角英数字で入力してください")
   private String username;

   @NotEmpty(message = "メールアドレスを入力してください")
   @Email(message = "メールアドレスが無効です")
   private String email;

   @NotEmpty(message = "パスワードを入力してください")
   @Size(min = 8, max = 12, message = "パスワードは8~12文字で入力してください")
   @Pattern(regexp = "^[A-Za-z0-9]*$", message = "パスワードは半角英数字で入力してください")
   private String password;
	
   @Size(max = 10, message = "登録できる文字数は10文字以内です")
	private String firstName;
   
   @Size(max = 10, message = "登録できる文字数は10文字以内です")
	private String lastName;
   
	private String profileImage;
	
	@Size(max = 40, message = "登録できる文字数は40文字以内です")
	private String bio;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer status;
	private String role;
	private LocalDateTime emailVerifiedAt;
}





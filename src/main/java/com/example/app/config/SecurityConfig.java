package com.example.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	    http
//	        .authorizeHttpRequests(requests -> requests
//	            .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()  // 静的リソースへのアクセス許可
//	            .requestMatchers("/GameHive/register", "/GameHive/login", "/GameHive/successRegister").permitAll()  // 必要なページへのアクセス許可
//	            .anyRequest().authenticated()  // 他の全てのリクエストは認証を要求
//	        )
//	        // ログインフォーム設定                                    フィルターを使おう
//	        .formLogin(login -> login
//	            .loginPage("/GameHive/login")  // ログインページのURL設定
//	            .defaultSuccessUrl("/GameHive/successRegister", true)  // ログイン成功後のリダイレクト先
//	            .permitAll()  // ログインページには誰でもアクセスできるように
//	        )
//	        // ログアウト設定
//	        .logout(logout -> logout
//	            .logoutUrl("/GameHive/logout")  // ログアウトのURL
//	            .logoutSuccessUrl("/GameHive/login")  // ログアウト後に遷移するURL
//	            .permitAll()  // ログアウトも誰でもアクセスできるように
//	        );
//
//	    return http.build();  // 設定をビルドして返す
//	}

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // パスワードのエンコーディングにBCryptを使用
    }
}

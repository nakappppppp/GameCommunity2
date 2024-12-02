package com.example.app.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初期化処理（必要に応じて実装）
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 前処理: 認証処理などを行う
        System.out.println("Authentication Filter - Before processing request");

        // 次のフィルターまたはリソースにリクエストを渡す
        chain.doFilter(request, response);

        // 後処理: レスポンス処理などを行う
        System.out.println("Authentication Filter - After processing request");
    }

    @Override
    public void destroy() {
        // クリーンアップ処理（必要に応じて実装）
    }
}

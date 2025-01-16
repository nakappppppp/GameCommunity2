package com.example.app.websocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpSession;

//WebSocket接続時に、HTTPセッションの情報を利用できるようにするクラス
public class HttpSessionHandshakeInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		// HTTPセッションからユーザー情報を取得
		HttpSession httpSession = ((ServletServerHttpRequest) request).getServletRequest().getSession(false);
		
		if (httpSession == null) {
	    return false;  // セッションが無い場合は接続を拒否
	}

		if (httpSession != null) {
			// HttpSessionからユーザー情報を取り出し、WebSocketセッションの属性に追加
			Integer userId = (Integer) httpSession.getAttribute("userId");
			String username = (String) httpSession.getAttribute("username");

			// WebSocketセッションにユーザー情報を保存
			if (userId != null) {
				attributes.put("userId", userId);
			}
			if (username != null) {
				attributes.put("username", username);
			}
		}

		return true; // ハンドシェイクを続行
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		// 必要なら後処理（例: ログ記録など）
	}
}

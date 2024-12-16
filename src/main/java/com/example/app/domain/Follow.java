package com.example.app.domain;

import lombok.Data;

@Data

public class Follow {

	 private Long followerId;   // フォローするユーザーのID
   private Long followedId;   // フォローされるユーザーのID
   
// フォロー関係を表すコンストラクタ
   public Follow(Long followerId, Long followedId) {
       this.followerId = followerId;
       this.followedId = followedId;
   }
}

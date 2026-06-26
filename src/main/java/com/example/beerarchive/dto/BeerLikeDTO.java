package com.example.beerarchive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerLikeDTO {
    private Long beerId;
    private int likeCount;
    private boolean liked; // 현재 로그인 유저가 좋아요 눌렀는지 여부
    
}

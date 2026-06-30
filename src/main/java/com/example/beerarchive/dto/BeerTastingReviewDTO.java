package com.example.beerarchive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerTastingReviewDTO {
    private Long reviewId;
    private Long accountId;
    private Long beerId;
    private String nickname;
    private String beerName;
    private double rating;
    private String content;

}

package com.example.beerarchive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerPairingDTO {
    private Long beerPairingId;
    private Long beerId;
    private String beerName;
    private String foodCategory;
    private String foodEmoji;
    private String description;
    private int likeCount;

}

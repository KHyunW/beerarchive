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
    private Long beerLikeId;
    private Long AccountId;
    private Long beerId;
    
}

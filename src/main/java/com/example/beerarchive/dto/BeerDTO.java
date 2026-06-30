package com.example.beerarchive.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDTO {
    private Long beerId;
    private String beerName;
    private String beerStyle;
    private double beerAbv;
    private int beerIbu;
    private Long breweryId;
    private String breweryName;
    private Long accountId;
    private int likeCount;
    private LocalDateTime createdAt;
    private String imagePath;
    
}

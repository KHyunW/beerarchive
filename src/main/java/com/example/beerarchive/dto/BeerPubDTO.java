package com.example.beerarchive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerPubDTO {
    private Long pubId;
    private String pubName;
    private String pubAddress;
    private double pubLatitude;
    private double pubLongitude;
    
}

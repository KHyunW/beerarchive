package com.example.beerarchive.entity;

import com.example.beerarchive.common.FoodCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerPairing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beerPairingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BEER_ID", nullable = false)
    private Beer beer;
    @Enumerated(EnumType.STRING)
    @Column(name = "FOOD_CATEGORY", nullable = false)
    private FoodCategory foodCategory;
    @Column(nullable = false)
    private String description;
    @Column(columnDefinition = "INT DEFAULT 0")
    private int likeCount = 0;
}

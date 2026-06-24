package com.example.beerarchive.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_ID", nullable = false)
    private Food food;
    @Column(nullable = false)
    private String description;
    @Builder.Default
    @Column(nullable = false)
    private int likeCount = 0;

    public void increaseLikeCount(){
        this.likeCount += 1;
    }
}

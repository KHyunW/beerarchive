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
public class Beer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BREWERY_ID", nullable = false)
    private Brewery brewery;
    @Column(nullable = false)
    private String beerName;
    @Column(nullable = false)
    private String beerStyle;
    @Column(nullable = false)
    private double beerAbv;
    @Column(nullable = false)
    private int beerIbu;
    @Column(nullable = true)
    private String imagePath;
}

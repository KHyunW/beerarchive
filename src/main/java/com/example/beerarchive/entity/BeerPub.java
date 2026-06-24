package com.example.beerarchive.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class BeerPub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pubId;
    @Column(nullable = false)
    private String pubName;
    @Column(nullable = false)
    private String pubAddress;
    @Column(nullable = false)
    private double pubLatitude;
    @Column(nullable = false)
    private double pubLongitude;
}

package com.example.beerarchive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beerarchive.entity.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {
    
    boolean existsByBeerName(String beerName);

    List<Beer> findByBeerNameContaining(String keyword);

    List<Beer> findByBeerStyle(String beerStyle);

    List<Beer> findByBeerNameContainingAndBeerStyle(String keyword, String beerStyle);

    List<Beer> findByBrewery_BreweryId(Long breweryId);

}

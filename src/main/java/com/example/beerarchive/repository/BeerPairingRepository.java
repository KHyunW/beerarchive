package com.example.beerarchive.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beerarchive.common.FoodCategory;
import com.example.beerarchive.entity.BeerPairing;

@Repository
public interface BeerPairingRepository extends JpaRepository<BeerPairing, Long> {

    List<BeerPairing> findByBeer_BeerId(Long beerId);
    
    List<BeerPairing> findByFoodCategoryAndBeer_BeerIdNot(FoodCategory foodCategory, Long beerId);

    boolean existsByBeer_BeerIdAndFoodCategory(Long beerId, FoodCategory foodCategory);

    Optional<BeerPairing> findByBeer_BeerIdAndFoodCategory(Long beerId, FoodCategory foodCategory);
}

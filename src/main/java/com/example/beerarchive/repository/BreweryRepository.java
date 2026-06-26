package com.example.beerarchive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beerarchive.entity.Brewery;

@Repository
public interface BreweryRepository extends JpaRepository<Brewery, Long>{
    
    boolean existsByBreweryName(String breweryName);

    List<Brewery> findByBreweryNameContaining(String keyword);
}

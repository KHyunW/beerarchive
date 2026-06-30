package com.example.beerarchive.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beerarchive.entity.BeerLike;

@Repository
public interface BeerLikeRepository extends JpaRepository<BeerLike, Long> {
    
    boolean existsByAccount_AccountIdAndBeer_BeerId(Long accountId, Long beerId);

    Optional<BeerLike> findByAccount_AccountIdAndBeer_BeerId(Long accountId, Long beerId);

    int countByBeer_BeerId(Long beerId);

    List<BeerLike> findByAccount_AccountId(Long accountId);
}

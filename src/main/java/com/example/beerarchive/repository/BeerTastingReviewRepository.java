package com.example.beerarchive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.beerarchive.entity.BeerTastingReview;

@Repository
public interface BeerTastingReviewRepository extends JpaRepository<BeerTastingReview, Long> {
    List<BeerTastingReview> findByBeer_BeerId(Long beerId);

    List<BeerTastingReview> findByAccount_AccountId(Long accountId);

    @Query("SELECT AVG(r.rating) FROM BeerTastingReview r WHERE r.beer.beerId = :beerId")
    Double findAvgRatingByBeerId(@Param("beerId") Long beerId);

    boolean existsByAccount_AccountIdAndBeer_BeerId(Long accountId, Long beerId);
}

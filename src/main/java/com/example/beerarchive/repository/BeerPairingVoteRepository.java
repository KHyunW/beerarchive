package com.example.beerarchive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beerarchive.entity.BeerPairingVote;

public interface BeerPairingVoteRepository extends JpaRepository<BeerPairingVote, Long>{
    
    boolean existsByAccount_AccountIdAndBeer_BeerId(Long accountId, Long beerId);

    List<BeerPairingVote> findByAccount_AccountId(Long accountId);

}

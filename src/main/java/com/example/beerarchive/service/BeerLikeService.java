package com.example.beerarchive.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.beerarchive.dto.BeerLikeDTO;
import com.example.beerarchive.entity.Account;
import com.example.beerarchive.entity.Beer;
import com.example.beerarchive.entity.BeerLike;
import com.example.beerarchive.repository.AccountRepository;
import com.example.beerarchive.repository.BeerLikeRepository;
import com.example.beerarchive.repository.BeerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BeerLikeService {
    
    private final BeerLikeRepository beerLikeRepository;
    private final BeerRepository beerRepository;
    private final AccountRepository accountRepository;

    public void likeToggle(Long accountId, Long beerId){
        
        if(beerLikeRepository.existsByAccount_AccountIdAndBeer_BeerId(accountId, beerId)){
            // 좋아요 취소
            BeerLike beerlike = beerLikeRepository
                    .findByAccount_AccountIdAndBeer_BeerId(accountId, beerId)
                    .orElseThrow();
            beerLikeRepository.delete(beerlike);
        } else {
            // 좋아요
            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
            Beer beer = beerRepository.findById(beerId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 맥주입니다"));
                
            BeerLike beerLike = BeerLike.builder()
                    .account(account)
                    .beer(beer)
                    .build();
            beerLikeRepository.save(beerLike);
        }
    }

    // 특정 맥주 좋아요 정보 조회
    public BeerLikeDTO getLikeInfo(Long beerId, Long accountId){
        boolean liked = accountId != null && beerLikeRepository.existsByAccount_AccountIdAndBeer_BeerId(accountId, beerId);
        int likeCount = beerLikeRepository.countByBeer_BeerId(beerId);

        return BeerLikeDTO.builder()
                .beerId(beerId)
                .likeCount(likeCount)
                .liked(liked)
                .build();
    }

}

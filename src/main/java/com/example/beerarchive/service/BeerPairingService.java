package com.example.beerarchive.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.beerarchive.common.FoodCategory;
import com.example.beerarchive.dto.BeerPairingDTO;
import com.example.beerarchive.entity.Account;
import com.example.beerarchive.entity.Beer;
import com.example.beerarchive.entity.BeerPairing;
import com.example.beerarchive.entity.BeerPairingVote;
import com.example.beerarchive.repository.BeerPairingRepository;
import com.example.beerarchive.repository.BeerPairingVoteRepository;
import com.example.beerarchive.repository.BeerRepository;
import com.example.beerarchive.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BeerPairingService {

    private final BeerPairingRepository beerPairingRepository;
    private final BeerPairingVoteRepository beerPairingVoteRepository;
    private final BeerRepository beerRepository;
    private final AccountRepository accountRepository;

    // 특정 맥주의 페어링 목록 조회
    public List<BeerPairingDTO> getPairingByBeer(Long beerId) {
        return beerPairingRepository.findByBeer_BeerId(beerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 같은 카테고리와 어울리는 다른 맥주 조회
    public List<BeerPairingDTO> getOtherBeersByCategory(String category, Long beerId) {
        FoodCategory foodCategory = FoodCategory.valueOf(category);
        return beerPairingRepository
                .findByFoodCategoryAndBeer_BeerIdNot(foodCategory, beerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 페어링 등록 (중복 시 좋아요 증가)
    public void register(Long beerId, Long accountId, String category) {
        FoodCategory foodCategory = FoodCategory.valueOf(category);

        if(beerPairingVoteRepository.existsByAccount_AccountIdAndBeer_BeerId(accountId, beerId)){
            throw new IllegalArgumentException("이미 이 맥주에 안주를 추천하셨습니다.");
        }

        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 맥주입니다"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        BeerPairing pairing;

        if (beerPairingRepository.existsByBeer_BeerIdAndFoodCategory(beerId, foodCategory)) {
            pairing = beerPairingRepository
                    .findByBeer_BeerIdAndFoodCategory(beerId, foodCategory)
                    .orElseThrow();
            pairing.setLikeCount(pairing.getLikeCount() + 1);
        } else {
            pairing = BeerPairing.builder()
                    .beer(beer)
                    .account(account)
                    .foodCategory(foodCategory)
                    .likeCount(1)
                    .build();
            beerPairingRepository.save(pairing);
        }

        BeerPairingVote vote = BeerPairingVote.builder()
                .account(account)
                .beer(beer)
                .beerPairing(pairing)
                .build();
        beerPairingVoteRepository.save(vote);
    }

    private BeerPairingDTO toDTO(BeerPairing pairing) {
        return BeerPairingDTO.builder()
                .beerPairingId(pairing.getBeerPairingId())
                .beerId(pairing.getBeer().getBeerId())
                .beerName(pairing.getBeer().getBeerName())
                .foodCategory(pairing.getFoodCategory().name())
                .foodEmoji(pairing.getFoodCategory().getEmoji())
                .likeCount(pairing.getLikeCount())
                .build();
    }

}

package com.example.beerarchive.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.beerarchive.common.FoodCategory;
import com.example.beerarchive.dto.BeerPairingDTO;
import com.example.beerarchive.entity.Beer;
import com.example.beerarchive.entity.BeerPairing;
import com.example.beerarchive.repository.BeerPairingRepository;
import com.example.beerarchive.repository.BeerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerPairingService {

    private final BeerPairingRepository beerPairingRepository;
    private final BeerRepository beerRepository;

    // 특정 맥주의 페어링 목록 조회
    public List<BeerPairingDTO> getPairingByBeer(Long beerId) {
        return beerPairingRepository.findByBeer_BeerId(beerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 같은 카테고리와 어울리는 다른 맥주 조회
    public List<BeerPairingDTO> getOtherBeersByCategory(String category, Long beerId){
        FoodCategory foodCategory = FoodCategory.valueOf(category);
        return beerPairingRepository
                .findByFoodCategoryAndBeer_BeerIdNot(foodCategory, beerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 페어링 등록 (중복 시 좋아요 증가)
    public void register(Long beerId, String category, String description){
        FoodCategory foodCategory = FoodCategory.valueOf(category);
        Beer beer = beerRepository.findById(beerId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 맥주입니다"));
        if (beerPairingRepository.existsByBeer_BeerIdAndFoodCategory(beerId, foodCategory)){
            BeerPairing existing = beerPairingRepository
                    .findByBeer_BeerId(beerId).stream()
                    .filter(p -> p.getFoodCategory() == foodCategory)
                    .findFirst()
                    .orElseThrow();
            existing.setLikeCount(existing.getLikeCount() + 1);
            beerPairingRepository.save(existing);
            return;
        }
        
        BeerPairing pairing = new BeerPairing();
        pairing.setBeer(beer);
        pairing.setFoodCategory(foodCategory);
        pairing.setDescription(description);
        beerPairingRepository.save(pairing);
    }

    // 좋아요 증가
    public void like(Long beerPairingId){
        BeerPairing pairing = beerPairingRepository.findById(beerPairingId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 페어링입니다"));
        pairing.setLikeCount(pairing.getLikeCount() + 1);
        beerPairingRepository.save(pairing);
    }

    private BeerPairingDTO toDTO(BeerPairing pairing) {
        BeerPairingDTO dto = new BeerPairingDTO();
        dto.setBeerPairingId(pairing.getBeerPairingId());
        dto.setBeerId(pairing.getBeer().getBeerId());
        dto.setBeerName(pairing.getBeer().getBeerName());
        dto.setFoodCategory(pairing.getFoodCategory().name());
        dto.setFoodEmoji(pairing.getFoodCategory().getEmoji());
        dto.setDescription(pairing.getDescription());
        dto.setLikeCount(pairing.getLikeCount());
        return dto;
    }

}

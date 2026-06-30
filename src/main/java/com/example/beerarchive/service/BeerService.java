package com.example.beerarchive.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.beerarchive.dto.BeerDTO;
import com.example.beerarchive.entity.Account;
import com.example.beerarchive.entity.Beer;
import com.example.beerarchive.entity.Brewery;
import com.example.beerarchive.repository.AccountRepository;
import com.example.beerarchive.repository.BeerLikeRepository;
import com.example.beerarchive.repository.BeerRepository;
import com.example.beerarchive.repository.BreweryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BeerService {

    private final BeerRepository beerRepository;
    private final BreweryRepository breweryRepository;
    private final BeerLikeRepository beerLikeRepository;
    private final AccountRepository accountRepository;

    // 맥주 목록 조회
    public List<BeerDTO> getBeerList(String keyword, String beerStyle) {
        List<Beer> beerList;
        if (keyword != null && !keyword.isEmpty() && beerStyle != null && !beerStyle.isEmpty()) {
            beerList = beerRepository.findByBeerNameContainingAndBeerStyle(keyword, beerStyle);
        } else if (keyword != null && !keyword.isEmpty()) {
            beerList = beerRepository.findByBeerNameContaining(keyword);
        } else if (beerStyle != null && !beerStyle.isEmpty()) {
            beerList = beerRepository.findByBeerStyle(beerStyle);
        } else {
            beerList = beerRepository.findAll();
        }

        return beerList.stream().map(this::toDTO).sorted((a, b) -> b.getLikeCount() - a.getLikeCount()).collect(Collectors.toList());
    }

    // 양조장별 맥주 목록 조회
    public List<BeerDTO> getBeersByBrewery(Long breweryId){
        return beerRepository.findByBrewery_BreweryId(breweryId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 맥주 단건 조회
    public BeerDTO getBeer(Long beerId){
        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 맥주입니다"));
        return toDTO(beer);
    }

    // 맥주 등록
    public Long register(BeerDTO dto, Long accountId){
        if(beerRepository.existsByBeerName(dto.getBeerName())){
            throw new IllegalArgumentException("이미 등록된 맥주입니다");
        }

        Brewery brewery = breweryRepository.findById(dto.getBreweryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 양조장입니다."));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        Beer beer = Beer.builder()
                .beerName(dto.getBeerName())
                .beerStyle(dto.getBeerStyle())
                .beerAbv(dto.getBeerAbv())
                .beerIbu(dto.getBeerIbu())
                .brewery(brewery)
                .account(account)
                .createdAt(LocalDateTime.now())
                .build();

        return beerRepository.save(beer).getBeerId();
    }

    public boolean isEditable(Long beerId, Long accountId){
        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 맥주입니다"));

        boolean isOwner = beer.getAccount().getAccountId().equals(accountId);
        boolean withinTime = LocalDateTime.now()
                .isBefore(beer.getCreatedAt().plusMinutes(5));

        return isOwner && withinTime;
    }

    // 맥주 수정
    public void update(Long beerId, BeerDTO dto){
        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 맥주입니다"));

        beer.setBeerName(dto.getBeerName());
        beer.setBeerStyle(dto.getBeerStyle());
        beer.setBeerAbv(dto.getBeerAbv());
        beer.setBeerIbu(dto.getBeerIbu());
    }

    // 맥주 삭제
    public void delete(Long beerId){
        beerRepository.deleteById(beerId);
    }

    private BeerDTO toDTO(Beer beer){
        return BeerDTO.builder()
                .beerId(beer.getBeerId())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .beerAbv(beer.getBeerAbv())
                .beerIbu(beer.getBeerIbu())
                .breweryId(beer.getBrewery().getBreweryId())
                .breweryName(beer.getBrewery().getBreweryName())
                .accountId(beer.getAccount().getAccountId())
                .likeCount(beerLikeRepository.countByBeer_BeerId(beer.getBeerId()))
                .createdAt(beer.getCreatedAt())
                .build();
    }
}

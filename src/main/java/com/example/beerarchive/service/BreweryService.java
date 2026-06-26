package com.example.beerarchive.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.beerarchive.dto.BreweryDTO;
import com.example.beerarchive.entity.Brewery;
import com.example.beerarchive.repository.BreweryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BreweryService {
    
    private final BreweryRepository breweryRepository;

    // 전체 양조장 목록
    public List<BreweryDTO> getAllBerweries(){
        return breweryRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 양조장 검색
    public List<BreweryDTO> searchBreweries(String keyword){
        return breweryRepository.findByBreweryNameContaining(keyword).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 양조장 조회
    public BreweryDTO getBrewery(Long breweryId){
        Brewery brewery = breweryRepository.findById(breweryId)
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 양조장입니다"));
        return toDTO(brewery);
    }

    // 양조장 등록
    public void register(String breweryName){
        if(breweryRepository.existsByBreweryName(breweryName)){
            throw new IllegalArgumentException("이미 등록된 양조장입니다");
        }
        Brewery brewery = new Brewery();
        brewery.setBreweryName(breweryName);
        breweryRepository.save(brewery);
    }

    // 양조장 삭제
    public void delete(Long breweryId){
        breweryRepository.deleteById(breweryId);
    }

    private BreweryDTO toDTO(Brewery brewery){
        BreweryDTO dto = new BreweryDTO();
        dto.setBreweryId(brewery.getBreweryId());
        dto.setBreweryName(brewery.getBreweryName());
        return dto;
    }
}

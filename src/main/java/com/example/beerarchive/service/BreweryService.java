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

    public List<BreweryDTO> getAllBerweries(){
        return breweryRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private BreweryDTO toDTO(Brewery brewery){
        BreweryDTO dto = new BreweryDTO();
        dto.setBreweryId(brewery.getBreweryId());
        dto.setBreweryName(brewery.getBreweryName());
        return dto;
    }
}

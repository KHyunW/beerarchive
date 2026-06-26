package com.example.beerarchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.service.BeerPairingService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pairing")
public class BeerPairingController {
    
    private final BeerPairingService beerPairingService;

    // 페어링 등록
    @PostMapping("/register")
    public String like(@RequestParam Long beerId,
                        @RequestParam String foodCategory,
                        @RequestParam String description,
                        HttpSession session){
        AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
        if(loginUser == null) {
            return "redirect:/auth/login";
        }

        beerPairingService.register(beerId, foodCategory, description);
        return "redirect:/beer/detail" + beerId;
    }
    
}

package com.example.beerarchive.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.dto.BeerPairingDTO;
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
    public String register(@RequestParam Long beerId,
                        @RequestParam String foodCategory,
                        HttpSession session,
                        RedirectAttributes redirectAttributes){
        AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
        if(loginUser == null) {
            return "redirect:/auth/login";
        } try {
            beerPairingService.register(beerId, loginUser.getAccountId(), foodCategory);
        } catch(IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("pairingError", e.getMessage());
        }
        return "redirect:/beer/detail/" + beerId;
    }

    @GetMapping("/others")
    @ResponseBody
    public List<BeerPairingDTO> getOtherBeers(@RequestParam String category,
                                            @RequestParam long beerId){
        return beerPairingService.getOtherBeersByCategory(category, beerId);
    }
    
}

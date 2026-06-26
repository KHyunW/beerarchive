package com.example.beerarchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.service.BeerLikeService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
public class BeerLikeController {
    
    private final BeerLikeService beerLikeService;

    // 좋아요 토글
    @PostMapping("/{beerId}")
    public String likeToggle(@PathVariable Long beerId, HttpSession session){
        
        AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
        if (loginUser == null){
            return "redirect:/auth/login";
        }
        
        beerLikeService.likeToggle(loginUser.getAccountId(), beerId);
        return "redirect:/beer/detail" + beerId;
    }
}

package com.example.beerarchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.service.BeerTastingReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class BeerTastingReviewController {
    
    private final BeerTastingReviewService reviewService;

    // 리뷰 등록
    @PostMapping("/register")
    public String register(@RequestParam Long beerId,
                            @RequestParam double rating,
                            @RequestParam String content,
                            HttpSession session,
                            RedirectAttributes redirectAttributes){
        AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
        if(loginUser == null){
            return "redirect:/auth/login";
        } try {
            reviewService.register(beerId, loginUser.getAccountId(), rating, content);
        } catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("reviewError", e.getMessage());
        }
        return "redirect:/beer/detail/" + beerId;
    }

    // 리뷰 삭제
    @PostMapping("/delete/{reviewId}")
    public String delete(@PathVariable Long reviewId,
                        @RequestParam Long beerId,
                        HttpSession session){
        AccountDTO loginUser = (AccountDTO) session.getAttribute("loginUser");
        if(loginUser == null){
            return "redirect:/auth/login";
        }

        reviewService.delete(reviewId, loginUser.getAccountId());
        return "redirect:/beer/detail/" + beerId;
    }
    
    
}

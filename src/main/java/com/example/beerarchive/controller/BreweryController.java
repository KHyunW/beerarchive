package com.example.beerarchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.beerarchive.service.BreweryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brewery")
public class BreweryController {
    
    private final BreweryService breweryService;

    // 양조장 목록 + 검색
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword, Model model){
        if(keyword != null && !keyword.isEmpty()){
            model.addAttribute("breweryList", breweryService.searchBreweries(keyword));
        } else {
            model.addAttribute("breweryList", breweryService.getAllBerweries());
        }
        model.addAttribute("keyword", keyword);
        return "brewery/list";
    }

    // 양조장 등록 폼
    @GetMapping("/register")
    public String resgisterForm(HttpSession session){
        if(session.getAttribute("loginUser") == null){
            return "redirect:/auth/login";
        }
        return "brewery/register";
    }

    // 양조장 등록 처리
    @PostMapping("/register")
    public String register(@RequestParam String breweryName, Model model, HttpSession session){
        if(session.getAttribute("loginUser") == null){
            return "redirect:/auth/login";
        }
        try {
            breweryService.register(breweryName);
            return "redirect:/brewery/list";
        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "brewery/register";
        }
    }

    // 양조장 삭제
    @PostMapping("/delete/{breweryId}")
    public String delete(@PathVariable Long breweryId, HttpSession session){
        if(session.getAttribute("loginUser") == null){
            return "redirect:/auth/login";
        }
        breweryService.delete(breweryId);
        return "redirect:/brewery/list";
    }
}

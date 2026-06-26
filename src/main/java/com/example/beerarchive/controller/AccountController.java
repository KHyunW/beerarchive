package com.example.beerarchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.service.AccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/register")
    public String registerForm(){
        return "account/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String email,
                        @RequestParam String nickname,
                        Model model){
        try{
            accountService.register(username, password, email, nickname);
            return "redirect:/auth/login";
        } catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "account/register";
        }

    }

}

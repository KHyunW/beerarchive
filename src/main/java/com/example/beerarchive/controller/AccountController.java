package com.example.beerarchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.service.AccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/register")
    public void registerForm(){}

    @PostMapping("/register")
    public String register(@ModelAttribute AccountDTO accountDTO){
        accountService.register(accountDTO);
        return "redirect:/auth/login";
    }
}

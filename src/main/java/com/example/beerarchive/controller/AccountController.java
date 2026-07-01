package com.example.beerarchive.controller;

import com.example.beerarchive.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.beerarchive.service.AccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountRepository accountRepository;
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

    @GetMapping("/check/username")
    @ResponseBody
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username){
        return ResponseEntity.ok(accountRepository.existsByUsername(username));
    }

    @GetMapping("/check/nickname")
    @ResponseBody
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname){
        return ResponseEntity.ok(accountRepository.existsByNickname(nickname));
    }

    @GetMapping("/check/email")
    @ResponseBody
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email){
        return ResponseEntity.ok(accountRepository.existsByEmail(email));
    }

}

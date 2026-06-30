package com.example.beerarchive.controller;

import com.example.beerarchive.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.beerarchive.dto.AccountDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model){

        try{
            AccountDTO loginUser = authService.login(username, password);
            session.setAttribute("loginUser", loginUser);
            return "redirect:/beer/list";
        } catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/beer/list";
    }

    @GetMapping("/temp-login")
    public String tempLogin(HttpSession session) {
        AccountDTO tempUser = AccountDTO.builder()
                .accountId(1L)
                .username("test")
                .password("1234")
                .email("test@test.com")
                .nickname("test")
                .build();
        session.setAttribute("loginUser", tempUser);
        return "redirect:/beer/list";
    }
}

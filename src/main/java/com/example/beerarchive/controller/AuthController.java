package com.example.beerarchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.beerarchive.dto.AccountDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    // @GetMapping("/login")
    // public void loginForm(String errorMsg, String logoutMsg, Model model) {
    //     if (errorMsg != null) {
    //         model.addAttribute("errorMgs", "아이디 비밀번호 확인");
    //     }
    //     if (logoutMsg != null) {
    //         model.addAttribute("logougMsg", "로그아웃 되었습니다");
    //     }
    // }
        // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
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

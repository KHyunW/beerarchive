package com.example.beerarchive.service;

import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.entity.Account;
import com.example.beerarchive.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;

    public AccountDTO login(String username, String password) {

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return AccountDTO.builder()
                .accountId(account.getAccountId())
                .username(account.getUsername())
                .email(account.getEmail())
                .nickname(account.getNickname())
                .role(account.getRole())
                .build();
    }
}
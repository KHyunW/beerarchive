package com.example.beerarchive.service;

import com.example.beerarchive.common.AccountRole;
import com.example.beerarchive.entity.Account;
import com.example.beerarchive.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    public void register(String username, String password,
                         String email, String nickname) {

        if (accountRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (accountRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (accountRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        Account account = Account.builder()
                .username(username)
                .password(password) // 암호화 없이 그대로 저장
                .email(email)
                .nickname(nickname)
                .role(AccountRole.ROLE_USER)
                .build();

        accountRepository.save(account);
    }

    public void changePassword(Long accountId, String currentPassword, String newPassword){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        if(!account.getPassword().equals(currentPassword)){
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다");
        }

        account.setPassword(newPassword);
    }
}
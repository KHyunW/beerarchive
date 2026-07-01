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


    // ------- 등급 시스템 --------
    
    // 맥주 등록 시 +10점
    public void addBeerPoint(Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        account.setPoint(account.getPoint() + 10);
    }

    // 리뷰 작성 시 +5점
    public void addReviewPoint(Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        account.setPoint(account.getPoint() + 5);
    }

    // 포인트 차감 (맥주 삭제)
    public void subtractBeerPoint(Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        int newPoint = Math.max(0, account.getPoint() - 10);
        account.setPoint(newPoint);
    }

    // 포인트 차감 (리뷰 삭제)
    public void subtractReviewPoint(Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        int newPoint = Math.max(0, account.getPoint() - 5);
        account.setPoint(newPoint);
    }

}
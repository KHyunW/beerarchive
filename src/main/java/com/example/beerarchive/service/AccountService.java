package com.example.beerarchive.service;

import org.springframework.stereotype.Service;

import com.example.beerarchive.common.AccountRole;
import com.example.beerarchive.dto.AccountDTO;
import com.example.beerarchive.entity.Account;
import com.example.beerarchive.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public void createAdmin(){
        if(accountRepository.findByUsername("admin").isPresent()){
            return;
        }
        
        Account admin = Account.builder()
                        .username("admin")
                        .password("1234")
                        .email("admin@test.com")
                        .nickname("관리자")
                        .role(AccountRole.ROLE_ADMIN)
                        .build();
        accountRepository.save(admin);
    }



    public int register(AccountDTO accountDTO){
        
        

        Account account = Account.builder()
                        .username(accountDTO.getUsername())
                        .password(accountDTO.getPassword())
                        .email(accountDTO.getEmail())
                        .nickname(accountDTO.getNickname())
                        .role(AccountRole.ROLE_USER)
                        .build();
        accountRepository.save(account);
        return 1;
    }
}

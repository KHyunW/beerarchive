package com.example.beerarchive.dto;

import com.example.beerarchive.common.AccountRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private Long accountId;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private AccountRole role;
    
}

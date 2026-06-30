package com.example.beerarchive.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beerarchive.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    
    Optional<Account> findByUsername(String username);

    List<Account> findByUsernameContainingOrNicknameContaining(String username, String nickname);
}

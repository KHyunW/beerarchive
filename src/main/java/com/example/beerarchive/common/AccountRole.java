package com.example.beerarchive.common;

import lombok.Getter;

@Getter
public enum AccountRole {
    ROLE_ADMIN(1, "관리자"),
    ROLE_USER(2, "사용자");

    private final int id;
    private final String gradeName;

    private AccountRole(int id, String gradeName){
        this.id = id;
        this.gradeName = gradeName;
    }

    public static AccountRole fromId(int id){
        for(AccountRole role : AccountRole.values()){
            if(role.getId() == id){
                return role;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 아이디 : " + id);
    }

}

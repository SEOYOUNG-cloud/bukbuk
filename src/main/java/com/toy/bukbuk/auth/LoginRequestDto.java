package com.toy.bukbuk.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;
}

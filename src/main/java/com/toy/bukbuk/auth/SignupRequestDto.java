package com.toy.bukbuk.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String email;
    private String password;
}

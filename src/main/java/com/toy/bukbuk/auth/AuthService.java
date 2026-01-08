package com.toy.bukbuk.auth;

import com.toy.bukbuk.entity.User;
import com.toy.bukbuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto signupRequest) {
        //  Check for duplicate email
        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPasswd = passwordEncoder.encode(signupRequest.getPassword());
        User user = new User(signupRequest.getEmail(), encodedPasswd);

        userRepository.save(user);
    }
}

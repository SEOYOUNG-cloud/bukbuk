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

    public void signup(SignupRequestDto request) {
        //  Check for duplicate email
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPasswd = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPasswd);

        userRepository.save(user);
    }
}

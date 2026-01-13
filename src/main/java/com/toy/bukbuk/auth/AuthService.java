package com.toy.bukbuk.auth;

import com.toy.bukbuk.auth.exception.BusinessException;
import com.toy.bukbuk.auth.exception.ErrorCode;
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
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATED);
        }

        String encodedPasswd = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPasswd);

        userRepository.save(user);
    }
}

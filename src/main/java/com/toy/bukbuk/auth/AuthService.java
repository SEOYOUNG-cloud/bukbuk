package com.toy.bukbuk.auth;

import com.toy.bukbuk.auth.exception.BusinessException;
import com.toy.bukbuk.auth.exception.ErrorCode;
import com.toy.bukbuk.entity.User;
import com.toy.bukbuk.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.toy.bukbuk.auth.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequestDto request) {
        //  Check for duplicate email
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATED);
        }

        String encodedPasswd = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPasswd);

        userRepository.save(user);
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {
        // email, pwd verify
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        user.updateRefreshToken(refreshToken);

        return new LoginResponseDto(accessToken, refreshToken);
    }
}

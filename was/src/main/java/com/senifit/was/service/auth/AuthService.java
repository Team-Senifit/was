package com.senifit.was.service.auth;

import com.senifit.was.repository.user.UserRepository;
import com.senifit.was.service.auth.exception.SignupValidationIdExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senifit.was.dto.request.auth.RegisterRequest;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(RegisterRequest dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new SignupValidationIdExistsException();
        }
        com.senifit.was.entity.User user = com.senifit.was.entity.User.builder()
                    .username(dto.getUsername())
                    .passwordHash(passwordEncoder.encode(dto.getPassword()))
                    .nickname(dto.getName())
                    .role(dto.getRole())
                    .build();
        userRepository.save(user);
    }
}

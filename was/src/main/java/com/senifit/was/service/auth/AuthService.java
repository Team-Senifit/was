package com.senifit.was.service.auth;

import com.senifit.was.entity.Centers;
import com.senifit.was.service.auth.exception.SignupValidationIdExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senifit.was.dto.request.auth.RegisterRequestDTO;
import com.senifit.was.repository.center.CentersRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class AuthService {

    private final CentersRepository centersRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(RegisterRequestDTO dto) {
        if (centersRepository.existsByid(dto.getId()))
           throw new SignupValidationIdExistsException();

        Centers center = Centers.builder()
            .id(dto.getId())
            .password(passwordEncoder.encode(dto.getPassword()))
            .name(dto.getName())
            .location(dto.getLocation())
            .role(dto.getRole())
            .build();

        centersRepository.save(center);
    }
}

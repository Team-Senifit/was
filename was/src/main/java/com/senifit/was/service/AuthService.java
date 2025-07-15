package com.senifit.was.service;

import com.senifit.was.entity.Centers;
import com.senifit.was.exception.custom.SignupValidationIdExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senifit.was.dto.request.auth.RegisterRequestDTO;
import com.senifit.was.entity.Users;
import com.senifit.was.repository.center.CentersRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class AuthService {

    @Autowired private CentersRepository centersRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public Centers signUp(RegisterRequestDTO dto) {
        if (!centersRepository.existsByid(dto.getId()))
           throw new SignupValidationIdExistsException();

        Centers center = Centers.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .location(dto.getLocation())
                .role(dto.getRole())
                .build();

        centersRepository.save(center);
        return centersRepository.findByid(dto.getId());
    }
}

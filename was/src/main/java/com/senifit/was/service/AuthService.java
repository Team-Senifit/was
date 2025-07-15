package com.senifit.was.service;

import com.senifit.was.entity.Centers;
import com.senifit.was.exception.custom.CenterExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senifit.was.dto.request.auth.RegisterRequestDTO;
import com.senifit.was.entity.Users;
import com.senifit.was.repository.center.CentersRepository;
import com.senifit.was.repository.user.UsersRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class AuthService {

    @Autowired
    private CentersRepository centersRepository;

    @Transactional
    public Users register(RegisterRequestDTO dto) {
        if (!centersRepository.existsByid(dto.getId()))
           throw new CenterExistsException();
    }
}

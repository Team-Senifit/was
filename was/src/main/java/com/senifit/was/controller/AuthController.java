package com.senifit.was.controller;

import com.senifit.was.common.response.ApiResponse;
import com.senifit.was.entity.Centers;
import com.senifit.was.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.senifit.was.dto.request.auth.RegisterRequestDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController("auth")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/auth/register")
    public ApiResponse<Centers> register(@RequestBody RegisterRequestDTO dto) {
        return new ApiResponse<Centers>(authService.signUp(dto));
    }

    // TODO: 
    // @GetMapping("/auth/signin")
    // @GetMapping("/auth/signout")
    // @GetMapping("/auth/info")
    // @GetMapping("/auth/update")
    
}

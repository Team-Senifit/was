package com.senifit.was.controller;

import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.entity.Centers;
import com.senifit.was.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.senifit.was.dto.request.auth.RegisterRequestDTO;
import org.springframework.web.bind.annotation.RequestBody;


@RestController("auth")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/auth/signup")
    public ApiResponse<Void> signUp(@RequestBody RegisterRequestDTO dto) {
        authService.signUp(dto);
        return ApiResponse.success();
    }

//    @GetMapping("/auth/info")
//    @PreAuthorize("isAuthenticated()")
//    public ApiResponse<Centers> info() {}

    // /auth/signin, signout 은 spring security가 처리.



    // TODO: 
    // @GetMapping("/auth/info")
    // @GetMapping("/auth/update")
    
}

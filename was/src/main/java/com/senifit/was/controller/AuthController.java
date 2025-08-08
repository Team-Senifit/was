package com.senifit.was.controller;

import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.senifit.was.dto.request.auth.RegisterRequest;
import org.springframework.web.bind.annotation.RequestBody;


@RestController("auth")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/auth/signup")
    public ApiResponse<Void> signUp(@RequestBody RegisterRequest dto) {
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

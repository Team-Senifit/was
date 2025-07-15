package com.senifit.was.controller;

import org.springframework.web.bind.annotation.RestController;
import com.senifit.was.dto.request.auth.RegisterRequestDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController("auth")
public class AuthController {

    @GetMapping("/auth/register")
    public String register(@RequestBody RegisterRequestDTO request) {
        return new String();
    }

    // TODO: 
    // @GetMapping("/auth/signin")
    // @GetMapping("/auth/signout")
    // @GetMapping("/auth/info")
    // @GetMapping("/auth/update")
    
}

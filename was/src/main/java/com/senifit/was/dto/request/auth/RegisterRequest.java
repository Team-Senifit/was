package com.senifit.was.dto.request.auth;

import com.senifit.was.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String name;
    private String location;
    private User.Role role;
}

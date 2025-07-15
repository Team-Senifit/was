package com.senifit.was.dto.request.auth;

import com.senifit.was.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterRequestDTO {
    private String id;
    private String password;
    private Gender name;
    private String location;
}

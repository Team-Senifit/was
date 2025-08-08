package com.senifit.was.dto.request.center;

import com.senifit.was.entity.CenterRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CenterCreateRequest {
    private String id;
    private String name;
    private String location;
    private String role;
    private String password;
}

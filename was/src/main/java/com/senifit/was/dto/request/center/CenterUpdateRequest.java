package com.senifit.was.dto.request.center;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CenterUpdateRequest {
    private Long centerCode;
    private String name;
    private String location;
    private String description;
}

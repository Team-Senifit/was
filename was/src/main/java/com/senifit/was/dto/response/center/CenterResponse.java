package com.senifit.was.dto.response.center;

import com.senifit.was.dto.response.member.MemberResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CenterResponse {

    private Long centerCode;
    private String name;
    private String description;
    private String location;
    private Integer memberCount;
    private List<MemberResponse> members;
}

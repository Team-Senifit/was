package com.senifit.was.dto.response.center;

import com.senifit.was.dto.response.member.MemberResponse;
import com.senifit.was.entity.CenterRole;
import com.senifit.was.entity.Members;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CenterResponse {

    private String name;

    private String location;

    private CenterRole role;

    private Integer memberCount;

    private List<MemberResponse> members;
}

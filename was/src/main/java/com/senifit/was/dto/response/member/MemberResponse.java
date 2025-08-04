package com.senifit.was.dto.response.member;

import com.senifit.was.entity.Gender;
import com.senifit.was.entity.MemberRank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {

    private Long memberId;

    private String name;

    private LocalDate birthDate;

    private Integer age;

    private Gender gender;

    private MemberRank memberRank;

}

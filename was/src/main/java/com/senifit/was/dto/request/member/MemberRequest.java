package com.senifit.was.dto.request.member;

import com.senifit.was.entity.Gender;
import com.senifit.was.entity.MemberRank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {

    private String name;

    private LocalDate birthDate;

    private Integer age;

    private Gender gender;

    private MemberRank memberRank;
}

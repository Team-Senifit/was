package com.senifit.was.dto.response.member;

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

    private Long gender;

    private Long memberRank;

}

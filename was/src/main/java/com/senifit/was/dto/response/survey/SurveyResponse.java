package com.senifit.was.dto.response.survey;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyResponse {

    private Long surveyId;

    private String name;

    private LocalDate birthDate;

    private Long gender;

    private Long memberRank;

    private List<TroublePartsResponse> troubleParts;

    private int attitudeScore;

    private int abilityScore;

    private String memo;

    private boolean hadTrouble;

    private boolean isDeleted;
}

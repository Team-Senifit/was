package com.senifit.was.dto.response.survey;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyResponse {

    private Long surveyId;

    private List<TroublePartsResponse> troubleParts;

    private int attitudeScore;

    private int abilityScore;

    private boolean hadTrouble;

    private LocalDateTime updatedAt;
}

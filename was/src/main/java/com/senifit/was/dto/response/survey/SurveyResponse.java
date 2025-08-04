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

    private int attitude;

    private int ability;

    private boolean trouble;

    private Long centerId;

    private LocalDateTime updatedAt;
}

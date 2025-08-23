package com.senifit.was.dto.response.record;

import com.senifit.was.dto.response.program.SimpleVideoResponse;
import com.senifit.was.dto.response.survey.SurveyResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordSurveyResponse {
    private RecordResponse record;
    private List<SimpleVideoResponse> routines;
    private List<SurveyResponse> surveys;
}

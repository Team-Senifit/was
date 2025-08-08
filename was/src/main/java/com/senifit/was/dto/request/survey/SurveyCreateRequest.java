package com.senifit.was.dto.request.survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyCreateRequest {

    private List<Integer> troubleParts;

    private int attitude;

    private int ability;

    private boolean trouble;
}

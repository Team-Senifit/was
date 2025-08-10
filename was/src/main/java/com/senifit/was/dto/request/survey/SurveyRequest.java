package com.senifit.was.dto.request.survey;

import com.senifit.was.entity.selections.TargetKind;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyRequest {

    private Long surveyId;

    private List<TargetKind> troubleParts;

    private int attitudeScore;

    private int abilityScore;

    private boolean hadTrouble;
}

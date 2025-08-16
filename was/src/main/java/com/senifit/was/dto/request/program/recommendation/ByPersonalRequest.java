package com.senifit.was.dto.request.program.recommendation;

import com.senifit.was.entity.selections.CognitiveWorkoutKind;
import com.senifit.was.entity.selections.DurationKind;
import com.senifit.was.entity.selections.IncludesSingingWorkout;
import com.senifit.was.entity.selections.TargetKind;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ByPersonalRequest {
    private DurationKind duration;
    private CognitiveWorkoutKind cognitive_workout_code;
    private TargetKind primary_target_code;
    private IncludesSingingWorkout signing_workout_code;
}

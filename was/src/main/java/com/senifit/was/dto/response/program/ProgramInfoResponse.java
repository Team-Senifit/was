package com.senifit.was.dto.response.program;

import com.senifit.was.entity.selections.CognitiveWorkoutKind;
import com.senifit.was.entity.selections.CooldownWorkoutKind;
import com.senifit.was.entity.selections.IncludesSingingWorkout;
import com.senifit.was.entity.selections.WarmupWorkoutKind;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Builder
public class ProgramInfoResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final Integer duration;

    private final WarmupWorkoutKind warmup_workout_code;
    private final CooldownWorkoutKind cooldown_workout_code;
    private final CognitiveWorkoutKind cognitive_workout_code;
    private final IncludesSingingWorkout singing_workout_code;

    private final String thumbnail_path;
}

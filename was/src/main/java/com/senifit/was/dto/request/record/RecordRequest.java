package com.senifit.was.dto.request.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senifit.was.entity.selections.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordRequest {

    @NotNull
    private Long programId;
    private List<Long> participants;

    private RoutineKind routineKind;
    private CognitiveWorkoutKind cognitiveKind;
    private IncludesSingingWorkout singingKind;
    private DurationKind durationKind;
    private TargetKind targetKind;
}

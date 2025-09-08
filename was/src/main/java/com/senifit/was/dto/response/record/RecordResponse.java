package com.senifit.was.dto.response.record;

import com.senifit.was.entity.lookup.LookupTarget;
import com.senifit.was.entity.selections.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordResponse {

    private Long recordId;

    private Long programId;

    private Integer duration;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Integer participantCount;

    private RoutineKind routineKind;

    private CognitiveWorkoutKind cognitiveKind;

    private IncludesSingingWorkout singingKind;

    private TargetKind targetKind;

    private boolean surveyExist;
}

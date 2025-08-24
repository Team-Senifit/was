package com.senifit.was.dto.response.record;

import com.senifit.was.entity.selections.CognitiveWorkoutKind;
import com.senifit.was.entity.selections.DurationKind;
import com.senifit.was.entity.selections.IncludesSingingWorkout;
import com.senifit.was.entity.selections.RoutineKind;
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

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Integer participantCount;

    private RoutineKind routineKind;

    private CognitiveWorkoutKind cognitiveKind;

    private IncludesSingingWorkout singingKind;

    private DurationKind durationKind;

    private boolean surveyExist;
}

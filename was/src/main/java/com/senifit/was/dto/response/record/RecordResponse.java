package com.senifit.was.dto.response.record;

import com.senifit.was.entity.selections.CognitiveKind;
import com.senifit.was.entity.selections.DurationKind;
import com.senifit.was.entity.selections.IncludesSinging;
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

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer participantCount;

    private RoutineKind routineKind;

    private CognitiveKind cognitiveKind;

    private IncludesSinging singingKind;

    private DurationKind durationKind;

    private boolean surveyExist;
}

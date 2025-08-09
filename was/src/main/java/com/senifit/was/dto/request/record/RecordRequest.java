package com.senifit.was.dto.request.record;

import com.senifit.was.entity.selections.RoutineKind;
import com.senifit.was.entity.selections.CognitiveKind;
import com.senifit.was.entity.selections.DurationKind;
import com.senifit.was.entity.selections.IncludesSinging;
import com.senifit.was.entity.selections.TargetKind;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private RoutineKind routineKind;
    private CognitiveKind cognitiveKind;
    private IncludesSinging singingKind;
    private DurationKind durationKind;
    private TargetKind targetKind;
}

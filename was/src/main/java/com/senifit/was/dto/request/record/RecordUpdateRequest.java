package com.senifit.was.dto.request.record;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordUpdateRequest {
    @NotBlank
    private Long programId;

    @NotBlank
    private Long centerId;

    private Integer participantCount;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer exerciseTimes;

    private String target;

    private boolean isSingingIncluded;
}

package com.senifit.was.dto.response.record;

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

    private Long centerId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer participantCount;

    private Integer exerciseTimes;

    private String target;

    private boolean isSingingIncluded;

    private boolean surveysExist;
}

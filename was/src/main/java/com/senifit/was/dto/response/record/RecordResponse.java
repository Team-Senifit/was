package com.senifit.was.dto.response.record;

import com.senifit.was.entity.ExerciseTimes;
import com.senifit.was.entity.MuscleType1;
import com.senifit.was.entity.Tools;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordResponse {

    private Long recordId;

    private Long programId;

    private Long centerId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer participantCount;

    private ExerciseTimes exerciseTimes;

    private Tools tools;

    private boolean taekwondo;

    private MuscleType1 muscleType1;
}

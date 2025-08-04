package com.senifit.was.dto.response.record;

import com.senifit.was.entity.ExerciseTimes;
import com.senifit.was.entity.MuscleType1;
import com.senifit.was.entity.Surveys;
import com.senifit.was.entity.Tools;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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

    private ExerciseTimes exerciseTimes;

    private Tools tools;

    private boolean taekwondo;

    private boolean surveysExist;

    private MuscleType1 muscleType1;
}

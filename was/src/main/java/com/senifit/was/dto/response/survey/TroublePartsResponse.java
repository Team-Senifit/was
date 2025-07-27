package com.senifit.was.dto.response.survey;

import com.senifit.was.entity.MuscleType1;
import com.senifit.was.entity.Surveys;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TroublePartsResponse {

    MuscleType1 muscleType1;
}

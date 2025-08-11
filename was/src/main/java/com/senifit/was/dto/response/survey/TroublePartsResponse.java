package com.senifit.was.dto.response.survey;

import com.senifit.was.entity.GlobalEnum;
import com.senifit.was.entity.selections.TargetKind;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TroublePartsResponse {
    TargetKind target;
}

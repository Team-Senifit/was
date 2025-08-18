package com.senifit.was.dto.request.program.recommendation;


import com.senifit.was.entity.selections.ProgramRecommendationPerTargetKind;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ByTargetRequest {
   private final ProgramRecommendationPerTargetKind workout_kind;
}

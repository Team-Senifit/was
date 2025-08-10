package com.senifit.was.entity.selections;

import com.senifit.was.entity.GlobalEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DurationKind implements BaseSelectionEnum {
    HALF(GlobalEnum.WORKOUT_DURATION_30MINUTES),
    HOUR(GlobalEnum.WORKOUT_DURATION_60MINUTES);

    private final GlobalEnum globalEnum;
}
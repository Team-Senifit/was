package com.senifit.was.entity.selections;

import com.senifit.was.entity.GlobalEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IncludesCognitive implements BaseSelectionEnum {
    YES(GlobalEnum.WORKOUT_KINDS_COGNITIVE),
    NO(GlobalEnum.WORKOUT_NOT_SELECTED);

    private final GlobalEnum globalEnum;
}
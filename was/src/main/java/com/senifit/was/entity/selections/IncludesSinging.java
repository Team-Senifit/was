package com.senifit.was.entity.selections;

import com.senifit.was.entity.GlobalEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IncludesSinging implements BaseSelectionEnum {
    YES(GlobalEnum.WORKOUT_KINDS_SINGING),
    NO(GlobalEnum.WORKOUT_NOT_SELECTED);

    private final GlobalEnum globalEnum;
}
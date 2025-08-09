package com.senifit.was.entity.selections;

import com.senifit.was.entity.GlobalEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoutineKind implements BaseSelectionEnum {
    POPULAR(GlobalEnum.WORKOUT_PROGRAMS_SELECTIONS_POPULAR),
    PERSONAL(GlobalEnum.WORKOUT_PROGRAMS_SELECTIONS_PERSONAL),
    TARGET(GlobalEnum.WORKOUT_PROGRAMS_SELECTIONS_TARGET);

    private final GlobalEnum globalEnum;
}

package com.senifit.was.entity.selections;

import com.senifit.was.entity.GlobalEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TargetKind implements BaseSelectionEnum {
    ARMS(GlobalEnum.WORKOUT_KINDS_CALISTHENIC_TARGETS_ARMS),
    SHOULDERS(GlobalEnum.WORKOUT_KINDS_CALISTHENIC_TARGETS_SHOULDERS),
    ARMS_AND_SHOULDERS(GlobalEnum.WORKOUT_KINDS_CALISTHENIC_TARGETS_ARMS_AND_SHOULDERS),
    ABS(GlobalEnum.WORKOUT_KINDS_CALISTHENIC_TARGETS_ABS),
    LEGS(GlobalEnum.WORKOUT_KINDS_CALISTHENIC_TARGETS_LEGS),
    BACK(GlobalEnum.WORKOUT_KINDS_CALISTHENIC_TARGETS_BACK);

    private final GlobalEnum globalEnum;
}
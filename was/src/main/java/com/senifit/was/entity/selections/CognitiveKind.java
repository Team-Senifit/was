package com.senifit.was.entity.selections;

import com.senifit.was.entity.GlobalEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CognitiveKind implements BaseSelectionEnum {
    TAEKWONDO(GlobalEnum.WORKOUT_KINDS_COGNITIVE_KINDS_TAEKWONDO),
    DUALTASKING(GlobalEnum.WORKOUT_KINDS_COGNITIVE_KINDS_DUALTASKING),
    CONTINUOUS(GlobalEnum.WORKOUT_KINDS_COGNITIVE_KINDS_CONTINUOUS),
    NOT_SELECTED(GlobalEnum.WORKOUT_NOT_SELECTED);

    private final GlobalEnum globalEnum;
}
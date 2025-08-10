package com.senifit.was.entity.selections.converter;

import com.senifit.was.entity.selections.RoutineKind;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoutineKindConverter extends AbstractSelectionEnumConverter<RoutineKind> {
    public RoutineKindConverter() {
        super(RoutineKind.class);
    }
}



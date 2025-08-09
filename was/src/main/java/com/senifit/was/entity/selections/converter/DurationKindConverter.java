package com.senifit.was.entity.selections.converter;

import com.senifit.was.entity.selections.DurationKind;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DurationKindConverter extends AbstractSelectionEnumConverter<DurationKind> {
    public DurationKindConverter() {
        super(DurationKind.class);
    }
}



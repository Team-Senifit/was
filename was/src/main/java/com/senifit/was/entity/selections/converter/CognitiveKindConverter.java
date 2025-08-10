package com.senifit.was.entity.selections.converter;

import com.senifit.was.entity.selections.CognitiveKind;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CognitiveKindConverter extends AbstractSelectionEnumConverter<CognitiveKind> {
    public CognitiveKindConverter() {
        super(CognitiveKind.class);
    }
}



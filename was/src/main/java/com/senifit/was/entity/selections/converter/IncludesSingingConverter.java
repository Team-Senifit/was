package com.senifit.was.entity.selections.converter;

import com.senifit.was.entity.selections.IncludesSinging;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IncludesSingingConverter extends AbstractSelectionEnumConverter<IncludesSinging> {
    public IncludesSingingConverter() {
        super(IncludesSinging.class);
    }
}



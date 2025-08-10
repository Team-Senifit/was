package com.senifit.was.entity.selections.converter;

import com.senifit.was.entity.selections.BaseSelectionEnum;
import jakarta.persistence.AttributeConverter;

/**
 * Base converter that maps selection enums to BIGINT ids in DB and back.
 */
public abstract class AbstractSelectionEnumConverter<E extends Enum<E> & BaseSelectionEnum>
        implements AttributeConverter<E, Long> {

    private final Class<E> enumType;

    protected AbstractSelectionEnumConverter(Class<E> enumType) {
        this.enumType = enumType;
    }

    @Override
    public Long convertToDatabaseColumn(E attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public E convertToEntityAttribute(Long dbData) {
        return BaseSelectionEnum.fromId(enumType, dbData);
    }
}



package com.senifit.was.entity.selections;

import com.senifit.was.entity.GlobalEnum;

/**
 * Marker/super interface for selection enums that wrap a {@link GlobalEnum}.
 * Provides a common accessor and helpers for code-based (de)serialization.
 */
public interface BaseSelectionEnum {

    /**
     * The backing global enum entry for this selection value.
     */
    GlobalEnum getGlobalEnum();

    /**
     * Convenience accessor for the global code string.
     */
    default String getCode() {
        GlobalEnum backing = getGlobalEnum();
        return backing == null ? null : backing.code;
    }

    /**
     * Convenience accessor for the global numeric id.
     */
    default Long getId() {
        GlobalEnum backing = getGlobalEnum();
        return backing == null ? null : backing.id;
    }

    /**
     * Resolve a selection enum constant by its global code string.
     */
    static <E extends Enum<E> & BaseSelectionEnum> E fromCode(Class<E> enumType, String code) {
        if (code == null) {
            return null;
        }
        for (E constant : enumType.getEnumConstants()) {
            GlobalEnum ge = constant.getGlobalEnum();
            if (ge != null && code.equals(ge.code)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("Unknown code '" + code + "' for enum " + enumType.getSimpleName());
    }

    /**
     * Resolve a selection enum constant by its global numeric id.
     */
    static <E extends Enum<E> & BaseSelectionEnum> E fromId(Class<E> enumType, Long id) {
        if (id == null) {
            return null;
        }
        for (E constant : enumType.getEnumConstants()) {
            GlobalEnum ge = constant.getGlobalEnum();
            if (ge != null && Long.valueOf(ge.id).equals(id)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("Unknown id '" + id + "' for enum " + enumType.getSimpleName());
    }
}



package com.senifit.was.entity;

public enum CenterRole {
    USER, ADMIN;

    public static CenterRole from(String value) {
        for (CenterRole role : values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("알 수 없는 CenterRole 값: " + value);
    }
}

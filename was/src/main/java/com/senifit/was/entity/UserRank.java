package com.senifit.was.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRank {
    Rank1(1, "1등급"),
    Rank2(2, "2등급"),
    Rank3(3, "3등급"),
    Rank4(4, "4등급"),
    Rank5(5, "5등급"),
    RankCognitiveSupport(6, "인지지원 등급");

    private final int integerLevel;
    private final String description;

    @JsonValue
    public int toValue() {
        return integerLevel;
    }

    @JsonCreator
    public static UserRank fromValue(int value) {
        for (UserRank rank : UserRank.values()) {
            if (rank.integerLevel == value) {
                return rank;
            }
        }
        throw new IllegalArgumentException("알 수 없는 UserRank.integerLevel: " + value);
    }
}
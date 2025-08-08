package com.senifit.was.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyTroublePartId implements Serializable {

    @Column(name = "survey_id")
    private Long surveyId;

    @Column(name = "target")
    private Long targetId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyTroublePartId that = (SurveyTroublePartId) o;
        return Objects.equals(surveyId, that.surveyId) && Objects.equals(targetId, that.targetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surveyId, targetId);
    }
}


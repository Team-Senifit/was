package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "surveys_trouble_parts")
public class SurveyTroublePart {

    @EmbeddedId
    private SurveyTroublePartId id = new SurveyTroublePartId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("surveyId")
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("targetId")
    @JoinColumn(name = "target")
    private LookupTarget target;

    @Builder
    public SurveyTroublePart(Survey survey, LookupTarget target) {
        this.survey = survey;
        this.target = target;
    }
}


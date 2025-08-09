package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "surveys")
public class Survey extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long surveyId;

    @Column(name = "center_id")
    private Long centerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_record_id")
    private MemberRecord memberRecord;

    @Column(name = "ability_score")
    private Integer abilityScore;

    @Column(name = "attitude_score")
    private Integer attitudeScore;

    @Column(name = "had_trouble")
    private Boolean hadTrouble;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyTroublePart> surveyTroubleParts = new ArrayList<>();

    @Builder
    public Survey(
            Long centerId,
            MemberRecord memberRecord,
            Integer abilityScore,
            Integer attitudeScore,
            Boolean hadTrouble,
            List<SurveyTroublePart> surveyTroubleParts) {
        this.centerId = centerId;
        this.memberRecord = memberRecord;
        this.abilityScore = abilityScore;
        this.attitudeScore = attitudeScore;
        this.hadTrouble = hadTrouble;
        if (surveyTroubleParts != null) {
            this.surveyTroubleParts = surveyTroubleParts;
        }
    }

    public void updateSurvey(int attitude, int ability, boolean trouble, Long centerId, List<SurveyTroublePart> newTroubleParts) {
        this.attitudeScore = attitude;
        this.abilityScore = ability;
        this.hadTrouble = trouble;
        this.centerId = centerId;
        this.surveyTroubleParts.clear();
        this.surveyTroubleParts.addAll(newTroubleParts);
    }
}


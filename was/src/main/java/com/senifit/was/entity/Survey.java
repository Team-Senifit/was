package com.senifit.was.entity;

import com.senifit.was.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_record_id", nullable = false, unique = true)
    private MemberRecord memberRecord;

    @Column(name = "ability_score")
    private Integer abilityScore;

    @Column(name = "attitude_score")
    private Integer attitudeScore;

    @Size(max = 200)
    @Column(name = "memo", length = 200)
    private String memo;

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
            String memo,
            Boolean hadTrouble,
            List<SurveyTroublePart> surveyTroubleParts) {
        this.centerId = centerId;
        this.memberRecord = memberRecord;
        this.abilityScore = abilityScore;
        this.attitudeScore = attitudeScore;
        this.memo = memo;
        this.hadTrouble = hadTrouble;
        if (surveyTroubleParts != null) {
            this.surveyTroubleParts = surveyTroubleParts;
        }
    }

    public void updateSurvey(int attitude, int ability, String memo, boolean trouble, Long centerId, List<SurveyTroublePart> newTroubleParts) {
        this.attitudeScore = attitude;
        this.abilityScore = ability;
        this.memo = memo;
        this.hadTrouble = trouble;
        this.centerId = centerId;
        this.surveyTroubleParts.clear();
        this.surveyTroubleParts.addAll(newTroubleParts);
    }
}


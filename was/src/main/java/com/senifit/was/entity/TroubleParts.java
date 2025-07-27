package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TroubleParts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long troublePartId;

    @Enumerated(EnumType.STRING)
    MuscleType1 muscleType1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    private Surveys surveys;

    @Builder
    public TroubleParts(MuscleType1 muscleType1, Surveys surveys) {
        this.muscleType1 = muscleType1;
        this.surveys = surveys;
    }
}

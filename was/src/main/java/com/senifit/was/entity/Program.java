package com.senifit.was.entity;

import com.senifit.was.entity.base.BaseTimeEntity;
import com.senifit.was.entity.lookup.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "programs")
public class Program extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "thumbnail_path", length = 255)
    private String thumbnailPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warmup_workout_kind_id")
    private LookupWorkoutWarmupKind warmupWorkoutKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooldown_workout_kind_id")
    private LookupWorkoutCooldownKind cooldownWorkoutKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cognitive_workout_kind_id")
    private LookupWorkoutCognitiveKind cognitiveWorkoutKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "singing_workout_kind_id")
    private LookupWorkoutSingingKind singingWorkoutKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_target_id")
    private LookupTarget primaryTarget;

    @Builder
    public Program(
            Long id,
            String name,
            String description,
            Integer duration,
            String thumbnailPath,
            LookupWorkoutWarmupKind warmupWorkoutKind,
            LookupWorkoutCooldownKind cooldownWorkoutKind,
            LookupWorkoutCognitiveKind cognitiveWorkoutKind,
            LookupWorkoutSingingKind singingWorkoutKind,
            LookupTarget primaryTarget
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnailPath = thumbnailPath;
        this.warmupWorkoutKind = warmupWorkoutKind;
        this.cooldownWorkoutKind = cooldownWorkoutKind;
        this.cognitiveWorkoutKind = cognitiveWorkoutKind;
        this.singingWorkoutKind = singingWorkoutKind;
        this.primaryTarget = primaryTarget;
    }
}


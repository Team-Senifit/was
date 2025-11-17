package com.senifit.was.entity;

import com.senifit.was.entity.base.BaseTimeEntity;
import com.senifit.was.entity.lookup.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "programs")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Program extends BaseTimeEntity {

    @Id
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

    @ManyToOne
    @JoinColumn(name = "specialized_kind_id")
    private LookupSpecializedWorkoutKind specializedWorkoutKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_target_id")
    private LookupTarget primaryTarget;

    // 엔티티 생성시 수동 주입 필요
    @Column(name = "hash")
    private String hash;

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Program))
            return false;
        return ((Program) other).getHash().equals(this.getHash());
    }
}


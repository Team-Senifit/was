package com.senifit.was.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private LookupContentKind warmupWorkoutKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooldown_workout_kind_id")
    private LookupContentKind cooldownWorkoutKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cognitive_workout_kind_id")
    private LookupContentKind cognitiveWorkoutKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "singing_workout_kind_id")
    private LookupContentKind singingWorkoutKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_target_id")
    private LookupTarget primaryTarget;

    @OneToMany(mappedBy = "program", orphanRemoval = true)
    private List<ProgramBundle> programBundles = new ArrayList<>();
}


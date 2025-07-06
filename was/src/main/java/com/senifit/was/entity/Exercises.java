package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Exercises {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exerciseId;

    private String name;
    private String description;

    private String path;
    private Level level;
    private MuscleType1 muscleType1;

    @OneToMany(mappedBy = "exercises", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExercisesToolTypes> exercisesToolTypes;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Programs programs;
}

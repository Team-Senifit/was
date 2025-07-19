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
public class Programs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ExerciseTimes exerciseTimes;

    @Enumerated(EnumType.STRING)
    private Tools tools;

    private boolean taekwondo;

    @Enumerated(EnumType.STRING)
    private MuscleType1 muscleType1;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "programs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercises> exercises;

    @OneToMany(mappedBy = "programs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Records> records;
}

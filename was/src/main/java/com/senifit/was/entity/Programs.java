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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "programs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercises> exercises;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    private Centers centers;
}

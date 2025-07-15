package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Records {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer participantCount;

    @OneToMany(mappedBy = "records", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Surveys> surveys;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Records(Long recordId, LocalDateTime startTime, LocalDateTime endTime, Integer participantCount, List<Surveys> surveys, LocalDateTime updatedAt) {
        this.recordId = recordId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participantCount = participantCount;
        this.surveys = surveys;
        this.updatedAt = updatedAt;
    }
}

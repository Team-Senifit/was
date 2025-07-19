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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private Centers centers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Programs programs;

    @OneToMany(mappedBy = "records", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Surveys> surveys;

    @OneToMany(mappedBy = "records", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecordsMembers> recordsMembers;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer participantCount;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Records(Long recordId, Centers centers, Programs programs, LocalDateTime startTime, LocalDateTime endTime, Integer participantCount, List<Surveys> surveys, LocalDateTime updatedAt) {
        this.recordId = recordId;
        this.centers = centers;
        this.programs = programs;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participantCount = participantCount;
        this.surveys = surveys;
        this.updatedAt = updatedAt;
    }

    public void updateRecordsMembers(List<RecordsMembers> recordsMembers) {
        this.recordsMembers = recordsMembers;
    }
}

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
public class Surveys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_member_id", nullable = false, unique = true)
    private RecordsMembers recordsMembers;

    @OneToMany(mappedBy = "surveys", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TroubleParts> troubleParts;

    private int attitude;

    private int ability;

    private boolean trouble;

    private Long centerId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Surveys(RecordsMembers recordsMembers, List<TroubleParts> troubleParts, int attitude, int ability, boolean trouble, Long centerId) {
        this.recordsMembers = recordsMembers;
        this.troubleParts = troubleParts;
        this.attitude = attitude;
        this.ability = ability;
        this.trouble = trouble;
        this.centerId = centerId;
    }

    public void updateSurvey(int attitude, int ability, boolean trouble, Long centerId, List<TroubleParts> newTroubleParts) {
        this.attitude = attitude;
        this.ability = ability;
        this.trouble = trouble;
        this.centerId = centerId;
        this.troubleParts.clear();
        this.troubleParts.addAll(newTroubleParts);
    }
}

package com.senifit.was.entity;

import com.senifit.was.entity.base.BaseTimeEntity;
import com.senifit.was.entity.lookup.LookupDurationKind;
import com.senifit.was.entity.lookup.LookupRoutineKind;
import com.senifit.was.entity.lookup.LookupWorkoutCognitiveKind;
import com.senifit.was.entity.lookup.LookupWorkoutSingingKind;
import com.senifit.was.entity.selections.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "records")
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private Center center;

    @Column(name = "program_id", nullable = false)
    private Long programId;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(name = "participant_count", nullable = false)
    private Integer participantCount;

    @Setter
    @JoinColumn(name = "routine_kind", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private LookupRoutineKind routineKind;

    @Setter
    @JoinColumn(name = "cognitive_kind", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private LookupWorkoutCognitiveKind cognitiveKind;

    @Setter
    @JoinColumn(name = "singing_kind", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private LookupWorkoutSingingKind includesSinging;

    @Setter
    @JoinColumn(name = "duration_kind", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private LookupDurationKind durationKind;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberRecord> memberRecords = new ArrayList<>();

    @Column(name = "survey_exist", nullable = false)
    private boolean surveyExist;

    @Builder
    public Record(
        Center center,
        Long programId,
        LocalDateTime startedAt,
        LocalDateTime finishedAt,
        Integer participantCount
    ) {
        this.center = center;
        this.programId = programId;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.participantCount = participantCount;
        this.surveyExist = false;
    }

    public void updateMemberRecords(List<MemberRecord> memberRecords) {
        this.memberRecords.clear();
        for (MemberRecord mr : memberRecords) {
            mr.setRecord(this);
            this.memberRecords.add(mr);
        }
    }

    public void updateSurveyExist() {
        this.surveyExist = true;
    }

    public void updateRecordFinishedAt() {
        this.finishedAt = LocalDateTime.now();
    }
}


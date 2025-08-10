package com.senifit.was.entity;

import com.senifit.was.entity.selections.CognitiveKind;
import com.senifit.was.entity.selections.DurationKind;
import com.senifit.was.entity.selections.IncludesSinging;
import com.senifit.was.entity.selections.RoutineKind;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "finished_at", nullable = false)
    private LocalDateTime finishedAt;

    @Column(name = "participant_count", nullable = false)
    private Integer participantCount;

    @Setter
    @Column(name = "routine_kind", nullable = false)
    private RoutineKind routineKind;

    @Setter
    @Column(name = "cognitive_kind", nullable = false)
    private CognitiveKind cognitiveKind;

    @Setter
    @Column(name = "singing_kind", nullable = false)
    private IncludesSinging includesSinging;

    @Setter
    @Column(name = "duration_kind", nullable = false)
    private DurationKind durationKind;

    @OneToMany(mappedBy = "record", orphanRemoval = true)
    private List<MemberRecord> memberRecords = new ArrayList<>();

    @Column(name = "survey_exist", nullable = false)
    private boolean surveyExist;

    @Builder
    public Record(
        Center center,
        Program program,
        LocalDateTime startedAt,
        LocalDateTime finishedAt,
        Integer participantCount
    ) {
        this.center = center;
        this.program = program;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.participantCount = participantCount;
        this.surveyExist = false;
    }

    public void updateMemberRecords(List<MemberRecord> memberRecords) {
        this.memberRecords = memberRecords;
    }

    public void updateSurveyExist() {
        this.surveyExist = true;
    }
}


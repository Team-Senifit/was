package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "record", orphanRemoval = true)
    private List<MemberRecord> memberRecords = new ArrayList<>();

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
    }

    public void updateMemberRecords(List<MemberRecord> memberRecords) {
        this.memberRecords = memberRecords;
    }
}


package com.senifit.was.entity;

import com.senifit.was.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "programs_stats")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramStat extends BaseTimeEntity {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @Id
    @Column(name = "program_id", insertable = false, updatable = false)
    private Long programId;

    @Column(name = "used_count", nullable = false)
    private Long usedCount;

    @Builder
    public ProgramStat(Long programId, Long usedCount) {
        this.programId = programId;
        this.usedCount = usedCount;
    }
}

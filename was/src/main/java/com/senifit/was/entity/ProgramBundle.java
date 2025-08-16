package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "programs_bundles")
public class ProgramBundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id", nullable = false)
    private Bundle bundle;

    @Column(name = "sequence")
    private Integer sequence;

    @Builder
    public ProgramBundle(Program program, Bundle bundle, Integer sequence, Long id) {
        this.program = program;
        this.bundle = bundle;
        this.sequence = sequence;
        this.id = id;
    }
}


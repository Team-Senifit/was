package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordsMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private Records records;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members members;

    @OneToMany(mappedBy = "recordsMembers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Surveys> surveys;

}

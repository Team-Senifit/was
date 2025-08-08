package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private Center center;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", nullable = false)
    private LookupRank rank;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id", nullable = false)
    private LookupGender gender;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<MemberRecord> memberRecords = new ArrayList<>();

    public Integer getAge() {
        if (birthDate == null) return null;
        LocalDate today = LocalDate.now();
        if (birthDate.isAfter(today)) return 0;
        return Period.between(birthDate, today).getYears();
    }

    @Builder
    public Member(
            Center center,
            LookupRank rank,
            String name,
            LocalDate birthDate,
            LookupGender gender
    ) {
        this.center = center;
        this.rank = rank;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}


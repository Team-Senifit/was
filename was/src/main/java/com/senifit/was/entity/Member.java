package com.senifit.was.entity;

import com.senifit.was.entity.base.BaseTimeEntity;
import com.senifit.was.entity.lookup.LookupGender;
import com.senifit.was.entity.lookup.LookupRank;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

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

    @Column(name = "is_solar", nullable = false)
    private Boolean isSolar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id", nullable = false)
    private LookupGender gender;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
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
            Boolean isSolar,
            LookupGender gender
    ) {
        this.center = center;
        this.rank = rank;
        this.name = name;
        this.birthDate = birthDate;
        this.isSolar = isSolar;
        this.gender = gender;
    }

    public void updateMember(String name, LocalDate birthDate, Boolean isSolar, LookupGender gender, LookupRank rank) {
        this.name = name;
        this.birthDate = birthDate;
        this.isSolar = isSolar;
        this.gender = gender;
        this.rank = rank;
    }
}


package com.senifit.was.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private Centers centers;

    @OneToMany(mappedBy = "members", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecordsMembers> recordsMembers;

    private String name;

    private Integer age;

    private Gender gender;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private MemberRank memberRank;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Members(Centers centers, String name, Gender gender, LocalDate birthDate, MemberRank memberRank) {
        this.centers = centers;
        this.name = name;
        this.age = calculateAge(birthDate);
        this.gender = gender;
        this.birthDate = birthDate;
        this.memberRank = memberRank;
    }

    public void updateMembers(String name, LocalDate birthDate, Gender gender, MemberRank memberRank) {
        this.name = name;
        this.birthDate = birthDate;
        this.age = calculateAge(birthDate);
        this.gender = gender;
        this.memberRank = memberRank;
    }

    /**
     * 만 나이 계산
     */
    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}

package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "centers")
public class Center extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "center_id")
    private String centerId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private CenterRole role;

    @Column(name = "password_hash")
    private String passwordHash;


    @OneToMany(mappedBy = "center", orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @Builder
    public Center(String passwordHash,
                  String name,
                  String location,
                  CenterRole role,
                  String centerId) {
        this.name = name;
        this.location = location;
        this.role = role;
        this.centerId = centerId;
        this.passwordHash = passwordHash;
    }
}


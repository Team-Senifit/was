package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "centers")
public class Center extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_id")
    private Long centerId;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private CenterRole role;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> Records = new ArrayList<>();

    @Builder
    public Center(String loginId, String password, String name, String location, CenterRole role) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.location = location;
        this.role = role;
    }

    public void updateCenter(String name, String location) {
        this.name = name;
        this.location = location;
    }
}


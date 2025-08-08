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

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "center_code", nullable = false, unique = true)
    private Long centerCode;

    @Column(name = "description", nullable = false, length = 255)
    private String description = "";

    @Column(name = "location")
    private String location;

    @ManyToMany(mappedBy = "centers")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "center", orphanRemoval = true)
    private List<Member> members = new ArrayList<>();


    @Builder
    public Center(String passwordHash,
                  String name,
                  String location,
                  String description) {
        this.name = name;
        this.location = location;
        this.description = description;
    }
}


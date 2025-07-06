package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Centers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long centerId;

    private String id;
    private String password;
    private String name;
    private String location;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "centers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Instructors> instructors;

    @OneToMany(mappedBy = "centers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Users> users;

    @OneToMany(mappedBy = "centers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Programs> programs;
}

package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Centers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long centerId;

    private String id;
    private String password; // hash + salt값
    private String name;
    private String location;

    @Enumerated(EnumType.STRING)
    private CenterRole role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "centers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Members> members;

    @OneToMany(mappedBy = "centers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Records> Records;

    @Builder
    public Centers(String id, String password, String name, String location, CenterRole role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.location = location;
        this.role = role;
    }
}

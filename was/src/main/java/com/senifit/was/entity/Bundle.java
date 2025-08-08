package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bundles")
public class Bundle extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kind_id", nullable = false)
    private LookupContentKind kind;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @OneToMany(mappedBy = "bundle", orphanRemoval = true)
    private List<ProgramBundle> programBundles = new ArrayList<>();

    @OneToMany(mappedBy = "bundle", orphanRemoval = true)
    private List<BundleVideo> bundleVideos = new ArrayList<>();
}


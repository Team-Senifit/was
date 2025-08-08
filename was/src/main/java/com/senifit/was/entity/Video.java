package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "videos")
public class Video extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "script")
    private String script;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "video_path", nullable = false, length = 512)
    private String videoPath;

    @Column(name = "thumbnail_path", nullable = false, length = 512)
    private String thumbnailPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kind_id", nullable = false)
    private LookupContentKind kind;

    @OneToMany(mappedBy = "video", orphanRemoval = true)
    private List<VideoPurpose> videoPurposes = new ArrayList<>();

    @OneToMany(mappedBy = "video", orphanRemoval = true)
    private List<BundleVideo> bundleVideos = new ArrayList<>();

}


package com.senifit.was.entity;

import com.senifit.was.entity.base.BaseTimeEntity;
import com.senifit.was.entity.lookup.LookupVideoKind;
import com.senifit.was.entity.lookup.LookupTarget;
import com.senifit.was.entity.lookup.LookupWorkoutPurpose;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    @JoinColumn(name = "kind_id")
    private LookupVideoKind kind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_kind_id")
    private LookupTarget targetKind;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "video_purposes",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "purpose_id")
    )
    private List<LookupWorkoutPurpose> purposes = new ArrayList<>();

    @Builder
    public Video(
            Long id, String name, String description, String script,
            Integer duration, String videoPath,
            String thumbnailPath, LookupVideoKind kind, LookupTarget targetKind,
            List<LookupWorkoutPurpose> purposes
            ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.script = script;
        this.duration = duration;
        this.videoPath = videoPath;
        this.thumbnailPath = thumbnailPath;
        this.kind = kind;
        this.targetKind = targetKind;
        this.purposes = purposes;
    }

}


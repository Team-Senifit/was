package com.senifit.was.entity;

import com.senifit.was.entity.base.BaseTimeEntity;
import com.senifit.was.entity.lookup.LookupVideoKind;
import com.senifit.was.entity.lookup.LookupTarget;
import com.senifit.was.entity.lookup.LookupWorkoutPurpose;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "videos")
@Getter @Setter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    @Setter(AccessLevel.NONE)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "video_purposes",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "purpose_id")
    )
    private List<LookupWorkoutPurpose> purposes = new ArrayList<>();

}


package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "video_purposes")
public class VideoPurpose {

    @EmbeddedId
    private VideoPurposeId id = new VideoPurposeId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("videoId")
    @JoinColumn(name = "video_id")
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("purposeId")
    @JoinColumn(name = "purpose_id")
    private LookupWorkoutPurpose purpose;
}


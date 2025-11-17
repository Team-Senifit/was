package com.senifit.was.entity;

import com.senifit.was.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "programs_videos")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProgramVideo extends BaseTimeEntity {

    @EmbeddedId
    private ProgramVideoId id;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

}
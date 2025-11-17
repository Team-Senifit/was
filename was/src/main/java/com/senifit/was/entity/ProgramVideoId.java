package com.senifit.was.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramVideoId implements Serializable {

    @Column(name = "program_id")
    private Long programId;

    @Column(name = "video_id")
    private Long videoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramVideoId that = (ProgramVideoId) o;
        return Objects.equals(programId, that.programId) && Objects.equals(videoId, that.videoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(programId, videoId);
    }
}


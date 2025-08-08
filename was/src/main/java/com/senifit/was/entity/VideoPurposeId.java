package com.senifit.was.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoPurposeId implements Serializable {

    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "purpose_id")
    private Long purposeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoPurposeId that = (VideoPurposeId) o;
        return Objects.equals(videoId, that.videoId) && Objects.equals(purposeId, that.purposeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoId, purposeId);
    }
}


package com.senifit.was.dto.response.program;

import com.senifit.was.entity.selections.VideoKind;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class VideoResponse {
    private final Long id;
    private final VideoKind kind_code;
    private final String name;
    private final String description;
    private final String script;
    private final Integer duration;
    private final String video_path;
    private final String thumbnail_path;
}

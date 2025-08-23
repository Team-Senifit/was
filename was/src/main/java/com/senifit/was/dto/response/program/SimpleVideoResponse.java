package com.senifit.was.dto.response.program;

import com.senifit.was.entity.selections.VideoKind;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class SimpleVideoResponse {
    private final Long id;
    private final String name;
    private final String thumbnail_path;
}

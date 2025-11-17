package com.senifit.was.vo;

import com.senifit.was.entity.selections.TargetKind;
import com.senifit.was.entity.selections.VideoKind;

public record VideoCacheKey (
    VideoKind kind,
    TargetKind targetKind
) {}

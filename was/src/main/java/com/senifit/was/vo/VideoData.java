package com.senifit.was.vo;

import com.senifit.was.entity.selections.TargetKind;
import com.senifit.was.entity.selections.VideoKind;
import com.senifit.was.entity.selections.WorkoutPurposeKind;
import lombok.Builder;

import java.util.List;

@Builder
public record VideoData(
    Long id,
    String name,
    String description,
    String script,
    Integer duration,
    String videoPath,
    String thumbnailPath,
    VideoKind kind,
    TargetKind targetKind,
    List<WorkoutPurposeKind> firstPriorityPurposes,
    List<WorkoutPurposeKind> secondPriorityPurposes
) {}
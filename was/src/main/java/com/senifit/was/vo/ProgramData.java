package com.senifit.was.vo;

import com.senifit.was.entity.selections.*;
import lombok.Builder;

import java.util.List;

@Builder
public record ProgramData (
    String name,
    String description,
    String thumbnailPath,
    Integer duration,
    WarmupWorkoutKind warmupWorkoutKind,
    CooldownWorkoutKind cooldownWorkoutKind,
    CognitiveWorkoutKind cognitiveWorkoutKind,
    IncludesSingingWorkout singingWorkoutKind,
    SpecializedWorkoutKind specializedWorkoutKind,
    TargetKind primaryTarget,
    List<VideoData> videos
) {}

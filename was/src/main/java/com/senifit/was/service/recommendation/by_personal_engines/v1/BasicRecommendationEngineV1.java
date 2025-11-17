package com.senifit.was.service.recommendation.by_personal_engines.v1;

import com.senifit.was.dto.request.program.recommendation.ByPersonalRequest;
import com.senifit.was.entity.selections.*;
import com.senifit.was.service.recommendation.by_personal_engines.BaseRecommendationEngine;
import com.senifit.was.vo.ProgramData;
import com.senifit.was.vo.VideoData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasicRecommendationEngineV1 extends BaseRecommendationEngine {
    private final MetaDataHelper metaDataHelper;
    private final AssembleHelper selectionHelper;

    @Override
    public ProgramData generate(ByPersonalRequest request) {
        List<VideoData> videoList = generateVideoList(request);

        return ProgramData.builder()
                .name("맞춤형 프로그램")
                .description("맞춤형 프로그램")
                .thumbnailPath(metaDataHelper.generateThumbnailPath(videoList))
                .duration(metaDataHelper.calculateDuration(videoList))
                .videos(videoList)
                .warmupWorkoutKind(WarmupWorkoutKind.workout_kinds_warmup)
                .cooldownWorkoutKind(CooldownWorkoutKind.workout_kinds_cooldown)
                .cognitiveWorkoutKind(request.getCognitive_workout_code())
                .singingWorkoutKind(request.getSinging_workout_code())
                .specializedWorkoutKind(SpecializedWorkoutKind.workout_notSelected)
                .primaryTarget(request.getPrimary_target_code())
                .build();

    }

    public List<VideoData> generateVideoList(ByPersonalRequest request) {
        if (request.getDuration().equals(DurationKind.workout_duration_30minutes)) {
            return by30Minutes(request);
        } else if (request.getDuration().equals(DurationKind.workout_duration_60minutes)) {
            return by60Minutes(request);
        } else {
            throw new IllegalArgumentException("Unsupported duration: " + request.getDuration());
        }
    }

    private List<VideoData> by30Minutes(ByPersonalRequest request) {
        boolean hasSinging = request.getSinging_workout_code().equals(IncludesSingingWorkout.workout_kinds_singing);
        boolean hasCognitive = !request.getCognitive_workout_code().equals(CognitiveWorkoutKind.workout_notSelected);

        if (hasSinging && hasCognitive) {
            return selectionHelper.duration30_singing_cognitive(request);
        } else if (hasSinging && !hasCognitive) {
            return selectionHelper.duration30_singing_noCognitive(request);
        } else if (!hasSinging && hasCognitive) {
            return selectionHelper.duration30_noSinging_cognitive(request);
        } else { // !hasSinging && !hasCognitive
            return selectionHelper.duration30_noSinging_noCognitive(request);
        }
    }

    private List<VideoData> by60Minutes(ByPersonalRequest request) {
        boolean hasSinging = request.getSinging_workout_code().equals(IncludesSingingWorkout.workout_kinds_singing);
        boolean hasCognitive = !request.getCognitive_workout_code().equals(CognitiveWorkoutKind.workout_notSelected);
        boolean isLeg = request.getPrimary_target_code().equals(TargetKind.workout_kinds_calisthenic_targets_legs);
        boolean isArmsAndShoulders = request.getPrimary_target_code().equals(TargetKind.workout_kinds_calisthenic_targets_armsAndShoulders);

        if (hasSinging && hasCognitive) {
            if (isLeg) {
                return selectionHelper.duration60_singing_cognitive_leg(request);
            } else { // not leg
                return selectionHelper.duration60_singing_cognitive_notLeg(request);
            }
        } else if (hasSinging && !hasCognitive) {
            if (isArmsAndShoulders) {
                return selectionHelper.duration60_singing_noCognitive_armsAndShoulders(request);
            } else { // not arms and shoulders
                return selectionHelper.duration60_singing_noCognitive_noArmsAndShoulders(request);
            }
        } else if (!hasSinging && hasCognitive) {
            if (isArmsAndShoulders) {
                return selectionHelper.duration60_noSinging_cognitive_armsAndShoulders(request);
            } else { // not arms and shoulders
                return selectionHelper.duration60_noSinging_cognitive_noArmsAndShoulders(request);
            }
        } else { // !hasSinging && !hasCognitive
            if (isLeg) {
                return selectionHelper.duration60_noSinging_noCognitive_leg(request);
            } else { // not leg
                return selectionHelper.duration60_noSinging_noCognitive_noLeg(request);
            }
        }
    }
}

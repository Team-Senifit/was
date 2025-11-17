package com.senifit.was.service.recommendation.by_personal_engines.v1;

import com.senifit.was.dto.request.program.recommendation.ByPersonalRequest;
import com.senifit.was.entity.selections.TargetKind;
import com.senifit.was.entity.selections.VideoKind;
import com.senifit.was.repository.video_data.api.VideoDataRepository;
import com.senifit.was.vo.VideoData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class AssembleHelper {
    private final VideoDataRepository videoDataRepository;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private void addRandomVideos(List<VideoData> targetList, List<VideoData> videos, int limitDuration) {
        if (limitDuration == 0) {
            return;
        }

        List<VideoData> availableVideos = new ArrayList<>(videos);
        int length = 0;

        while (length < limitDuration && !availableVideos.isEmpty()) {
            int index = random.nextInt(availableVideos.size());
            VideoData video = availableVideos.remove(index);
            targetList.add(video);
            length += video.duration();
        }
    }

    private void bulkAddSecondaryCalisthenicVideos(List<VideoData> targetList,
                                                   TargetKind primaryTarget, int limitDurationPerTarget) {
        ArrayList<TargetKind> targets = new ArrayList<>();
        Collections.addAll(targets, TargetKind.values());
        targets.remove(primaryTarget);
        Collections.shuffle(targets, random);
        targets.forEach(target ->
            addRandomVideos(targetList,
                videoDataRepository.findCalisthenicVideos(target)
            , limitDurationPerTarget));
    }

    private List<VideoData> assembleWithSecondaryTarget(
            ByPersonalRequest request,
            int primaryDurationByMinutes,
            int secondaryDurationPerVideoByMinutes,
            int cognitiveDurationByMinutes,
            int singingDurationByMinutes
    ) {
        List<VideoData> resultList = new ArrayList<>();

        addRandomVideos(resultList, videoDataRepository.findWarmupVideos(), 3*60);
        addRandomVideos(resultList, videoDataRepository.findCalisthenicVideos(request.getPrimary_target_code()), primaryDurationByMinutes*60);
        bulkAddSecondaryCalisthenicVideos(resultList, request.getPrimary_target_code(), secondaryDurationPerVideoByMinutes*60);
        addRandomVideos(resultList, videoDataRepository.findCognitiveVideos(
                VideoKind.fromId(request.getCognitive_workout_code().getId())), cognitiveDurationByMinutes*60);
        addRandomVideos(resultList, videoDataRepository.findSingingVideos(
                VideoKind.fromId(request.getSinging_workout_code().getId())), singingDurationByMinutes*60);
        addRandomVideos(resultList, videoDataRepository.findCooldownVideos(), 2*60);

        return resultList;
    }

    private List<VideoData> assembleWithNoSecondaryTarget(
            ByPersonalRequest request,
            int primaryDurationByMinutes,
            int cognitiveDurationByMinutes,
            int singingDurationByMinutes
    ) {
        List<VideoData> resultList = new ArrayList<>();

        addRandomVideos(resultList, videoDataRepository.findWarmupVideos(), 3*60);
        addRandomVideos(resultList, videoDataRepository.findCalisthenicVideos(request.getPrimary_target_code()), primaryDurationByMinutes*60);
        addRandomVideos(resultList, videoDataRepository.findCognitiveVideos(
                VideoKind.fromId(request.getCognitive_workout_code().getId())), cognitiveDurationByMinutes*60);
        addRandomVideos(resultList, videoDataRepository.findSingingVideos(
                VideoKind.fromId(request.getSinging_workout_code().getId())), singingDurationByMinutes*60);
        addRandomVideos(resultList, videoDataRepository.findCooldownVideos(), 2*60);

        return resultList;
    }

    public List<VideoData> duration30_singing_cognitive(ByPersonalRequest request) {
        return assembleWithNoSecondaryTarget(request, 5, 10, 10);
    }

    public List<VideoData> duration30_singing_noCognitive(ByPersonalRequest request) {
        return assembleWithNoSecondaryTarget(request, 15, 0, 10);
    }

    public List<VideoData> duration30_noSinging_cognitive(ByPersonalRequest request) {
        return assembleWithNoSecondaryTarget(request, 15, 10, 0);
    }

    public List<VideoData> duration30_noSinging_noCognitive(ByPersonalRequest request) {
        return assembleWithSecondaryTarget(request, 10, 5, 0, 0);
    }

    public List<VideoData> duration60_singing_cognitive_notLeg(ByPersonalRequest request) {
        return assembleWithSecondaryTarget(request, 10, 5, 10, 10);
    }

    public List<VideoData> duration60_singing_cognitive_leg(ByPersonalRequest request) {
        return assembleWithSecondaryTarget(request, 15, 10, 10, 10);
    }

    public List<VideoData> duration60_singing_noCognitive_noArmsAndShoulders(ByPersonalRequest request) {
        return assembleWithSecondaryTarget(request, 15, 10, 0, 10);
    }

    public List<VideoData> duration60_singing_noCognitive_armsAndShoulders(ByPersonalRequest request) {
        return assembleWithSecondaryTarget(request, 10, 5, 0, 10);
    }

    public List<VideoData> duration60_noSinging_cognitive_noArmsAndShoulders(ByPersonalRequest request) {
        return assembleWithSecondaryTarget(request, 15, 10, 10, 0);
    }

    public List<VideoData> duration60_noSinging_cognitive_armsAndShoulders(ByPersonalRequest request) {
        return assembleWithSecondaryTarget(request, 10, 5, 10, 0);
    }

    public List<VideoData> duration60_noSinging_noCognitive_noLeg(ByPersonalRequest request) {
        return assembleWithSecondaryTarget(request, 15, 10, 0, 0);
    }

    public List<VideoData> duration60_noSinging_noCognitive_leg(ByPersonalRequest request) {
        return assembleWithSecondaryTarget(request, 20, 15, 0, 0);
    }
}

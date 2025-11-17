package com.senifit.was.repository.video_data.impl;

import com.senifit.was.entity.Video;
import com.senifit.was.entity.lookup.LookupWorkoutPurpose;
import com.senifit.was.entity.selections.TargetKind;
import com.senifit.was.entity.selections.VideoKind;
import com.senifit.was.repository.video.api.VideoRepository;
import com.senifit.was.repository.video_data.api.VideoDataRepository;
import com.senifit.was.vo.VideoCacheKey;
import com.senifit.was.vo.VideoData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class VideoDataCacheRepository implements VideoDataRepository {
    private Map<VideoCacheKey, List<VideoData>> videoDataCache = null;

    public List<VideoData> findNonCalisthenicVideos(VideoKind kind) {
       return videoDataCache.get(new VideoCacheKey(kind, TargetKind.workout_notSelected));
    }
    public List<VideoData> findCalisthenicVideos(TargetKind targetKind) {
        return getVideoData(targetKind, videoDataCache);
    }

    public List<VideoData> findWarmupVideos() {
        return findNonCalisthenicVideos(VideoKind.workout_kinds_warmup);
    }

    public List<VideoData> findCooldownVideos() {
        return findNonCalisthenicVideos(VideoKind.workout_kinds_cooldown);
    }

    public List<VideoData> findCognitiveVideos(VideoKind cognitiveKind) {
        return findNonCalisthenicVideos(cognitiveKind);
    }

    public List<VideoData> findSingingVideos(VideoKind singingKind) {
        return findNonCalisthenicVideos(singingKind);
    }

    public void initializeCache(Map<VideoCacheKey, List<VideoData>> cache) {
        if (this.videoDataCache != null)
            throw new RuntimeException("video cache initialization can be only done once.");
        this.videoDataCache = cache;
    }

    private List<VideoData> getVideoData(TargetKind targetKind, Map<VideoCacheKey, List<VideoData>> videoDataCache) {
        List<VideoData> videos = new ArrayList<>();

        List<VideoData> ipsilateral =
                videoDataCache.get(
                        new VideoCacheKey(VideoKind.workout_kinds_calisthenic_kinds_ipsilateral, targetKind));
        List<VideoData> unilateral =
                videoDataCache.get(
                        new VideoCacheKey(VideoKind.workout_kinds_calisthenic_kinds_unilateral, targetKind));

        if (ipsilateral != null)
            videos.addAll(ipsilateral);
        if (unilateral != null)
            videos.addAll(unilateral);

        return videos;
    }


}

package com.senifit.was.bootstrap.cache;

import com.senifit.was.entity.Video;
import com.senifit.was.entity.lookup.LookupWorkoutPurpose;
import com.senifit.was.repository.video.api.VideoRepository;
import com.senifit.was.repository.video_data.impl.VideoDataCacheRepository;
import com.senifit.was.vo.VideoCacheKey;
import com.senifit.was.vo.VideoData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VideoDataCacheInitializer {
    private final VideoRepository videoRepository;
    private final VideoDataCacheRepository videoDataCacheRepository;

    @Transactional(readOnly = true)
    public void initialize() {
        List<Video> videos = videoRepository.findAll();
        Map<VideoCacheKey, List<VideoData>> videoDataCache = new HashMap<>();

        for (Video video : videos) {
            VideoCacheKey key = new VideoCacheKey(
                    video.getKind().toSelection(),
                    video.getTargetKind().toSelection());

            videoDataCache.computeIfAbsent(key, k -> new ArrayList<>());
            List<VideoData> list = videoDataCache.get(key);

            list.add(VideoData.builder()
                    .id(video.getId())
                    .name(video.getName())
                    .description(video.getDescription())
                    .script(video.getScript())
                    .duration(video.getDuration())
                    .videoPath(video.getVideoPath())
                    .thumbnailPath(video.getThumbnailPath())
                    .kind(video.getKind().toSelection())
                    .targetKind(video.getTargetKind().toSelection())
                    .firstPriorityPurposes(video.getFirstPriorityPurposes()
                            .stream().map(LookupWorkoutPurpose::toSelection)
                            .collect(Collectors.toList()))
                    .secondPriorityPurposes(video.getSecondPriorityPurposes()
                            .stream().map(LookupWorkoutPurpose::toSelection)
                            .collect(Collectors.toList()))
                    .build()
            );
        }

        videoDataCacheRepository.initializeCache(videoDataCache);
    }
}

package com.senifit.was.repository.video_data.api;

import com.senifit.was.entity.selections.TargetKind;
import com.senifit.was.entity.selections.VideoKind;
import com.senifit.was.vo.VideoData;

import java.util.List;

public interface VideoDataRepository {
     List<VideoData> findNonCalisthenicVideos(VideoKind kind);
     List<VideoData> findCalisthenicVideos(TargetKind targetKind);
     List<VideoData> findWarmupVideos();
     List<VideoData> findCooldownVideos();
     List<VideoData> findCognitiveVideos(VideoKind cognitiveKind);
     List<VideoData> findSingingVideos(VideoKind singingKind);
}

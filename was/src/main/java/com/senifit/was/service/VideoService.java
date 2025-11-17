package com.senifit.was.service;

import com.senifit.was.dto.response.program.VideoResponse;
import com.senifit.was.entity.Video;
import com.senifit.was.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Service s3Service;

    public VideoResponse buildVideoDto(Video video) {
        return VideoResponse.builder()
            .id(video.getId())
            .kind_code(video.getKind().toSelection())
            .name(video.getName())
            .description(video.getDescription())
            .script(video.getScript())
            .duration(video.getDuration())
            .video_path(s3Service.generatePresignedUrl(video.getVideoPath()))
            .thumbnail_path(s3Service.generatePresignedUrl(video.getThumbnailPath()))
            .build();
    }

    public List<VideoResponse> buildVideoDtoList(List<Video> videos) {
        return videos.stream()
                .map(this::buildVideoDto)
                .toList();
    }
}

package com.senifit.was.service.workoutData;

import com.senifit.was.dto.response.program.ProgramInfoResponse;
import com.senifit.was.dto.response.program.ProgramResponse;
import com.senifit.was.dto.response.program.VideoResponse;
import com.senifit.was.entity.Program;
import com.senifit.was.entity.Video;
import com.senifit.was.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutDataDtoService {
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

    public ProgramResponse buildProgramDtoWithVideos(Program program, List<Video> videos) {
        List<VideoResponse> videoResponses = buildVideoDtoList(videos);
        return buildProgramDtoWithVideoResponses(program, videoResponses);
    }
    public ProgramResponse buildProgramDtoWithVideoResponses(Program program, List<VideoResponse> videoResponses) {
        return ProgramResponse.builder()
            .id(program.getId())
            .name(program.getName())
            .description(program.getDescription())
            .duration(program.getDuration())
            .warmup_workout_code(program.getWarmupWorkoutKind().toSelection())
            .cooldown_workout_code(program.getCooldownWorkoutKind().toSelection())
            .cognitive_workout_code(program.getCognitiveWorkoutKind().toSelection())
            .singing_workout_code(program.getSingingWorkoutKind().toSelection())
            .primary_target_code(program.getPrimaryTarget().toSelection())
            .specialized_workout_code(program.getSpecializedWorkoutKind().toSelection())
            .thumbnail_path(s3Service.generatePresignedUrl(program.getThumbnailPath()))
            .videos(videoResponses)
            .build();
    }
    public ProgramInfoResponse buildProgramInfoDto(Program program) {
        return ProgramInfoResponse.builder()
            .id(program.getId())
            .name(program.getName())
            .description(program.getDescription())
            .duration(program.getDuration())
            .warmup_workout_code(program.getWarmupWorkoutKind().toSelection())
            .cooldown_workout_code(program.getCooldownWorkoutKind().toSelection())
            .cognitive_workout_code(program.getCognitiveWorkoutKind().toSelection())
            .singing_workout_code(program.getSingingWorkoutKind().toSelection())
            .primary_target_code(program.getPrimaryTarget().toSelection())
            .specialized_workout_code(program.getSpecializedWorkoutKind().toSelection())
            .thumbnail_path(s3Service.generatePresignedUrl(program.getThumbnailPath()))
            .build();
    }
}

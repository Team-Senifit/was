package com.senifit.was.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.response.program.ProgramInfoResponse;
import com.senifit.was.dto.response.program.ProgramResponse;
import com.senifit.was.dto.response.program.VideoResponse;
import com.senifit.was.entity.*;
import com.senifit.was.exception.custom.ProgramNotFoundException;
import com.senifit.was.repository.program.api.ProgramRepository;
import com.senifit.was.repository.program.api.ProgramVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final S3Service s3Service;
    private final VideoService videoService;
    private final ProgramRepository programRepository;
    private final ProgramVideoRepository programVideoRepository;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void save(Program program) {
       programRepository.save(program);
    }

    @Transactional
    public void saveProgramVideos(List<ProgramVideo> programVideos) {
        programVideoRepository.saveAll(programVideos);
    }

    @Transactional(readOnly = true)
    public Program getById(Long id) {
        return programRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Program getPersonalByHash(String hash) {
        return programRepository.getProgramByHash(hash);
    }

    // TODO: 현재 dto / entity 리턴하는게 섞여있음. 추후 useCase / service로 분리 리팩토링 필요.
    @Transactional(readOnly = true)
    public ProgramResponse getProgram(Long programId) {
        QProgram p = QProgram.program;
        QProgramVideo pv = QProgramVideo.programVideo;
        QVideo v = QVideo.video;

        List<Video> videos = queryFactory
                .select(v)
                .from(p)
                .join(pv).on(pv.id.programId.eq(p.id))
                .join(v).on(pv.id.videoId.eq(v.id))
                .where(p.id.eq(programId))
                .orderBy(pv.sequence.asc())
                .fetch();

        Program program = programRepository.findById(programId)
                .orElseThrow(ProgramNotFoundException::new);

        return buildProgramDtoWithVideos(program, videos);
    }

    public ProgramResponse buildProgramDtoWithVideos(Program program, List<Video> videos) {
        List<VideoResponse> videoResponses = videoService.buildVideoDtoList(videos);
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

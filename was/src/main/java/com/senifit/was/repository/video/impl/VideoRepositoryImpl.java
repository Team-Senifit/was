package com.senifit.was.repository.video.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.entity.*;
import com.senifit.was.repository.video.api.VideoRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VideoRepositoryImpl implements VideoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Video> findVideosByProgramIdOrderBySequenceAsc(Long programId) {
        QVideo video = QVideo.video;
        QProgramVideo programVideo = QProgramVideo.programVideo;

        return queryFactory
                .select(video)
                .from(video)
                .join(programVideo).on(programVideo.id.videoId.eq(video.id))
                .where(programVideo.id.programId.eq(programId))
                .orderBy(programVideo.sequence.asc())
                .fetch();
    }
}

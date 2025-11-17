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
        QBundleVideo bundleVideo = QBundleVideo.bundleVideo;
        QBundle bundle = QBundle.bundle;
        QProgramBundle programBundle = QProgramBundle.programBundle;

        return queryFactory
                .select(video)
                .from(video)
                .join(bundleVideo).on(bundleVideo.video.eq(video))
                .join(bundle).on(bundleVideo.bundle.eq(bundle))
                .join(programBundle).on(programBundle.bundle.eq(bundle))
                .where(programBundle.program.id.eq(programId))
                .orderBy(programBundle.sequence.asc(), bundleVideo.sequence.asc())
                .fetch();
    }
}

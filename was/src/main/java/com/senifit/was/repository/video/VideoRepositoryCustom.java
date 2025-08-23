package com.senifit.was.repository.video;

import com.senifit.was.entity.Video;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepositoryCustom {
    List<Video> findVideosByProgramIdOrderBySequenceAsc(Long programId);
}

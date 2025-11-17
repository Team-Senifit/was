package com.senifit.was.repository.program.api;

import com.senifit.was.entity.ProgramVideo;
import com.senifit.was.entity.ProgramVideoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramVideoRepository extends JpaRepository<ProgramVideo, ProgramVideoId> {
}

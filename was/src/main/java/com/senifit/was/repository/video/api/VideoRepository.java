package com.senifit.was.repository.video.api;

import com.senifit.was.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>, VideoRepositoryCustom {
}


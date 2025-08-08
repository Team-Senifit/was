package com.senifit.was.repository.bundle;

import com.senifit.was.entity.BundleVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BundleVideoRepository extends JpaRepository<BundleVideo, Long> {
}


package com.senifit.was.repository.bundle;

import com.senifit.was.entity.ProgramBundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramBundleRepository extends JpaRepository<ProgramBundle, Long> {
}


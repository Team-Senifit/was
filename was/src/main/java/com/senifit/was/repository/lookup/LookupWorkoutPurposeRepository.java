package com.senifit.was.repository.lookup;

import com.senifit.was.entity.LookupWorkoutPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookupWorkoutPurposeRepository extends JpaRepository<LookupWorkoutPurpose, Long> {}


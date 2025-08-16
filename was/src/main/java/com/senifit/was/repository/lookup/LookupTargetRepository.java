package com.senifit.was.repository.lookup;

import com.senifit.was.entity.lookup.LookupTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookupTargetRepository extends JpaRepository<LookupTarget, Long> {}


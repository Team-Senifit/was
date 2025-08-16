package com.senifit.was.repository.lookup;

import com.senifit.was.entity.lookup.LookupRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookupRankRepository extends JpaRepository<LookupRank, Long> {}


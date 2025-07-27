package com.senifit.was.repository.survey;

import com.senifit.was.entity.TroubleParts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TroublePartsRepository extends JpaRepository<TroubleParts, Long> {
}

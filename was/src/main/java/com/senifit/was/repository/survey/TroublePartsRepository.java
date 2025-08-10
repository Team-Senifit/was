package com.senifit.was.repository.survey;

import com.senifit.was.entity.SurveyTroublePart;
import com.senifit.was.entity.SurveyTroublePartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TroublePartsRepository extends JpaRepository<SurveyTroublePart, SurveyTroublePartId>, TroublePartsRepositoryCustom {
}

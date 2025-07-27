package com.senifit.was.repository.survey;

import com.senifit.was.entity.RecordsMembers;
import com.senifit.was.entity.Surveys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveysRepository extends JpaRepository<Surveys, Long>, SurveysRepositoryCustom{
    List<Surveys> findByRecordsMembers(RecordsMembers recordsMembers);

}

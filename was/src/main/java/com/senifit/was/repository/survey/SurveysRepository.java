package com.senifit.was.repository.survey;

import com.senifit.was.entity.MemberRecord;
import com.senifit.was.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveysRepository extends JpaRepository<Survey, Long>, SurveysRepositoryCustom{
    List<Survey> findByMemberRecord(MemberRecord memberRecord);

}

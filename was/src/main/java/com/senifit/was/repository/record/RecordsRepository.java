package com.senifit.was.repository.record;

import com.senifit.was.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordsRepository extends JpaRepository<Record, Long>, RecordsRepositoryCustom {
    Long deleteByRecordIdAndCenter_CenterId(Long recordId, Long centerId);

}

package com.senifit.was.repository.record;

import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordsRepository extends JpaRepository<Records, Long> {
    List<RecordResponse> findAllByCenters_CenterId(Long centerId);

    Long deleteByRecordIdAndCenters_CenterId(Long recordId, Long centerId);

    RecordResponse findByRecordId(Long recordId);
}

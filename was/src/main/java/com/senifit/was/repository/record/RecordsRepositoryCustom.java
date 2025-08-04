package com.senifit.was.repository.record;


import com.senifit.was.dto.response.record.RecordResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordsRepositoryCustom {
    List<RecordResponse> findAllRecordByCenterId(Long centerId);
    RecordResponse findRecordById(Long recordId);
}

package com.senifit.was.repository.record;


import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.entity.Video;
import org.springframework.stereotype.Repository;
import com.senifit.was.entity.Record;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordsRepositoryCustom {
    List<RecordResponse> findAllRecordByCenterId(Long centerId);
    Optional<RecordResponse> findRecordById(Long recordId, Long centerId);
    Optional<Record> findByRecordIdAndCenterId(Long recordId, Long centerId);
}

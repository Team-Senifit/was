package com.senifit.was.service;

import com.senifit.was.dto.request.record.RecordRequest;
import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.entity.Records;
import com.senifit.was.exception.custom.RecordNotFoundException;
import com.senifit.was.repository.record.RecordsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final RecordsRepository recordsRepository;

    /**
     * 목록 조회
     */
    public List<RecordResponse> getRecordsByCenterId(Long centerId) {
        List<Records> recordsList = recordsRepository.findAllByCenterId(centerId);
        return null;
    }

    /**
     * 상세 조회
     */
    public Record getRecordById(Long recordId) {
        return null;
    }

    /**
     * 기록 생성
     */
    @Transactional
    public Long addRecord(RecordRequest request, Long centerId) {
        return null;
    }

    /**
     * 기록 수정
     */
    @Transactional
    public Long updateRecord(Long recordId, RecordRequest request) {
        return null;
    }

    /**
     * 기록 삭제
     */
    @Transactional
    public void deleteRecord(Long recordId) {
        return;
    }

}

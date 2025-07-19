package com.senifit.was.service;

import com.senifit.was.dto.request.record.RecordRequest;
import com.senifit.was.dto.request.record.RecordUpdateRequest;
import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.entity.*;
import com.senifit.was.exception.api.common.NotFoundApiException;
import com.senifit.was.exception.custom.CenterNotFoundException;
import com.senifit.was.exception.custom.ProgramNotFoundException;
import com.senifit.was.exception.custom.MemberNotFoundException;
import com.senifit.was.repository.center.CentersRepository;
import com.senifit.was.repository.program.ProgramsRepository;
import com.senifit.was.repository.record.RecordsRepository;
import com.senifit.was.repository.user.MembersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final RecordsRepository recordsRepository;
    private final CentersRepository centersRepository;
    private final ProgramsRepository programsRepository;
    private final MembersRepository membersRepository;

    /**
     * 목록 조회
     */
    public List<RecordResponse> getRecordsByCenterId(Long centerId) {
        return recordsRepository.findAllByCenters_CenterId(centerId);
    }

    /**
     * 상세 조회
     */
    public RecordResponse getRecordById(Long recordId) {
        return recordsRepository.findByRecordId(recordId);
    }

    /**
     * 기록 생성
     */
    @Transactional
    public Long addRecord(RecordRequest request, Long centerId) {
        // 센터 조회
        Centers center = centersRepository.findById(centerId)
                .orElseThrow(CenterNotFoundException::new);

        // 프로그램 조회
        Programs program = programsRepository.findById(request.getProgramId())
                .orElseThrow(ProgramNotFoundException::new);

        // Record 생성
        Records record = Records.builder()
                .centers(center)
                .programs(program)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .participantCount(request.getParticipants() != null ? request.getParticipants().size() : 0)
                .build();

        // RecordsUsers 생성 및 연관관계 설정
        List<RecordsMembers> participants = request.getParticipants().stream()
                .map(userId -> {
                    Members user = membersRepository.findById(userId)
                            .orElseThrow(MemberNotFoundException::new);

                    RecordsMembers recordsUsers = new RecordsMembers();
                    recordsUsers.setRecords(record);
                    recordsUsers.setMembers(user);
                    return recordsUsers;
                })
                .collect(Collectors.toList());

        record.updateRecordsMembers(participants);

        return recordsRepository.save(record).getRecordId();
    }

    /**
     * 기록 수정
     */
    @Transactional
    public Long updateRecordById(Long recordId, RecordUpdateRequest request, Long centerId) {
        return null;
    }

    /**
     * 기록 삭제
     */
    @Transactional
    public Long deleteRecordById(Long recordId, Long centerId) {

        return recordsRepository.deleteByRecordIdAndCenters_CenterId(recordId, centerId);
    }

}

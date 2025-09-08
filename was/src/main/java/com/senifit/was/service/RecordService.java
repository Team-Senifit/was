package com.senifit.was.service;

import com.senifit.was.dto.request.record.RecordRequest;
import com.senifit.was.dto.request.record.RecordUpdateRequest;
import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.entity.*;
import com.senifit.was.entity.Record;
import com.senifit.was.entity.lookup.*;
import com.senifit.was.exception.custom.CenterNotFoundException;
import com.senifit.was.exception.custom.ProgramNotFoundException;
import com.senifit.was.exception.custom.MemberNotFoundException;
import com.senifit.was.exception.custom.RecordNotFoundException;
import com.senifit.was.repository.center.CentersRepository;
import com.senifit.was.repository.program.ProgramRepository;
import com.senifit.was.repository.record.RecordsRepository;
import com.senifit.was.repository.member.MembersRepository;
import com.senifit.was.repository.survey.SurveysRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final RecordsRepository recordsRepository;
    private final CentersRepository centersRepository;
    private final ProgramRepository programRepository;
    private final MembersRepository membersRepository;
    private final SurveysRepository surveysRepository;

    /**
     * 목록 조회
     */
    public List<RecordResponse> getRecordsByCenterId(Long centerId) {
        return recordsRepository.findAllRecordByCenterId(centerId);
    }

    /**
     * 상세 조회
     */
    public Optional<RecordResponse> getRecordById(Long centerId, Long recordId) {
        return recordsRepository.findRecordById(recordId, centerId);
    }

    /**
     * 기록 생성
     */
    @Transactional
    public Long addRecord(RecordRequest request, Long centerId) {
        // 센터 조회
        Center center = centersRepository.findById(centerId)
                .orElseThrow(CenterNotFoundException::new);

        // 프로그램 조회
        Program program = programRepository.findById(request.getProgramId())
                .orElseThrow(ProgramNotFoundException::new);

        // Record 생성
        Record record = Record.builder()
                .center(center)
                .programId(program.getId())
                .duration(program.getDuration())
                .startedAt(LocalDateTime.now())
                .finishedAt(null)
                .participantCount(request.getParticipants() != null ? request.getParticipants().size() : 0)
                .build();

        // 선택값 설정 (DB에는 converter로 BIGINT id 저장)
        if (request.getRoutineKind() != null) {
            record.setRoutineKind(LookupRoutineKind.fromSelection(request.getRoutineKind()));
        }
        if (request.getCognitiveKind() != null) {
            record.setCognitiveKind(LookupWorkoutCognitiveKind.fromSelection(request.getCognitiveKind()));
        }
        if (request.getSingingKind() != null) {
            record.setIncludesSinging(LookupWorkoutSingingKind.fromSelection(request.getSingingKind()));
        }
        if (request.getTargetKind() != null) {
            record.setTargetKind(LookupTarget.fromSelection(request.getTargetKind()));
        }

        // MemberRecord 생성 및 연관성 설정
        List<MemberRecord> participants = request.getParticipants().stream()
                .map(memberId -> {
                    Member member = membersRepository.findById(memberId)
                            .orElseThrow(MemberNotFoundException::new);

                    MemberRecord memberRecord = new MemberRecord(member, record);
                    memberRecord.setRecord(record);
                    memberRecord.setMember(member);
                    return memberRecord;
                })
                .collect(Collectors.toList());

        record.updateMemberRecords(participants);

        Record saved = recordsRepository.save(record);

        // Survey 생성 및 연관성 설정
        List<Survey> surveysToCreate = saved.getMemberRecords().stream()
                .filter(mr -> mr.getSurvey() == null)
                .map(mr -> {
                    Survey s = Survey.builder()
                            .centerId(centerId)
                            .memberRecord(mr)
                            .abilityScore(0)
                            .attitudeScore(0)
                            .hadTrouble(false)
                            .surveyTroubleParts(List.of())
                            .build();
                    mr.setSurvey(s);
                    return s;
                })
                .toList();

        if (!surveysToCreate.isEmpty()) {
            surveysRepository.saveAll(surveysToCreate);
        }

        return saved.getRecordId();
    }

    /**
     * 기록 수정
     */
    @Transactional
    public Long updateRecordById(Long recordId, RecordUpdateRequest request, Long centerId) {
        return null;
    }

    /**
     * 기록 종료
     */
    @Transactional
    public void updateRecordFinishAt(Long recordId, Long centerId) {
        Record record = recordsRepository.findByRecordIdAndCenterId(recordId, centerId)
                .orElseThrow(RecordNotFoundException::new);
        
        record.updateRecordFinishedAt();
    }

    /**
     * 기록 삭제
     */
    @Transactional
    public void deleteRecordById(Long recordId, Long centerId) {
        recordsRepository.deleteByRecordIdAndCenter_CenterId(recordId, centerId);
    }

}

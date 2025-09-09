package com.senifit.was.repository.record;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.entity.*;
import com.senifit.was.entity.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecordsRepositoryImpl implements RecordsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecordResponse> findAllRecordByCenterId(Long centerId) {
        QRecord records = QRecord.record;
        QProgram program = QProgram.program;

        // records
        List<Record> result = queryFactory
                .selectFrom(records)
                .where(records.center.centerId.eq(centerId))
                .orderBy(records.createdAt.desc())
                .fetch();

        return result.stream()
                .map(r -> RecordResponse.builder()
                        .recordId(r.getRecordId())
                        .programId(r.getProgramId())
                        .duration(r.getDuration())
                        .startedAt(r.getStartedAt())
                        .finishedAt(r.getFinishedAt())
                        .participantCount(r.getParticipantCount())
                        .surveyExist(r.isSurveyExist())
                        .routineKind(r.getRoutineKind().toSelection())
                        .cognitiveKind(r.getCognitiveKind().toSelection())
                        .singingKind(r.getIncludesSinging().toSelection())
                        .targetKind(r.getTargetKind().toSelection())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RecordResponse> findRecordById(Long recordId, Long centerId) {
        QRecord records = QRecord.record;

        Record r = queryFactory
                .selectFrom(records)
                .where(records.recordId.eq(recordId),
                        records.center.centerId.eq(centerId))
                .fetchOne();

        if (r == null) return null;

        return Optional.ofNullable(RecordResponse.builder()
                .recordId(r.getRecordId())
                .programId(r.getProgramId())
                .duration(r.getDuration())
                .startedAt(r.getStartedAt())
                .finishedAt(r.getFinishedAt())
                .participantCount(r.getParticipantCount())
                .routineKind(r.getRoutineKind().toSelection())
                .cognitiveKind(r.getCognitiveKind().toSelection())
                .singingKind(r.getIncludesSinging().toSelection())
                .targetKind(r.getTargetKind().toSelection())
                .surveyExist(r.isSurveyExist())
                .build());
    }

    @Override
    public Optional<Record> findByRecordIdAndCenterId(Long recordId, Long centerId) {
        QRecord r = QRecord.record;

        Record record = queryFactory
                .selectFrom(r)
                .where(
                        r.recordId.eq(recordId),
                        r.center.centerId.eq(centerId)
                )
                .fetchOne();

        return Optional.ofNullable(record);
    }

}

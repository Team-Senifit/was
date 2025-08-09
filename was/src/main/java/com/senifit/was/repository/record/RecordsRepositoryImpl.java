package com.senifit.was.repository.record;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.entity.QProgram;
import com.senifit.was.entity.QRecord;
import com.senifit.was.entity.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.senifit.was.entity.QSurvey.survey;

@Repository
@RequiredArgsConstructor
public class RecordsRepositoryImpl implements RecordsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecordResponse> findAllRecordByCenterId(Long centerId) {
        QRecord records = QRecord.record;
        QProgram program = QProgram.program;

        // records + programs fetch
        List<Record> result = queryFactory
                .selectFrom(records)
                .leftJoin(records.program, program).fetchJoin()
                .where(records.center.centerId.eq(centerId))
                .orderBy(records.createdAt.desc())
                .fetch();

        return result.stream()
                .map(r -> RecordResponse.builder()
                        .recordId(r.getRecordId())
                        .programId(r.getProgram().getId())
                        .centerId(r.getCenter().getCenterId())
                        .startTime(r.getStartedAt())
                        .endTime(r.getFinishedAt())
                        .participantCount(r.getParticipantCount())
                        .surveysExist(getSurveysExist(r.getRecordId()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public RecordResponse findRecordById(Long recordId) {
        QRecord records = QRecord.record;
        QProgram program = QProgram.program;

        Record r = queryFactory
                .selectFrom(records)
                .leftJoin(records.program, program).fetchJoin()
                .where(records.recordId.eq(recordId))
                .fetchOne();

        if (r == null) return null;

        return RecordResponse.builder()
                .recordId(r.getRecordId())
                .programId(r.getProgram().getId())
                .centerId(r.getCenter().getCenterId())
                .startTime(r.getStartedAt())
                .endTime(r.getFinishedAt())
                .participantCount(r.getParticipantCount())
                .surveysExist(getSurveysExist(r.getRecordId()))
                .build();
    }

    private boolean getSurveysExist(Long recordId) {

        return queryFactory
                .selectOne()
                .from(survey)
                .where(survey.memberRecord.record.recordId.eq(recordId))
                .fetchFirst() != null;
    }
}

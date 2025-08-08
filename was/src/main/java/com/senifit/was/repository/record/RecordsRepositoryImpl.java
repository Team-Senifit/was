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
                .where(records.center.id.eq(centerId))
                .orderBy(records.createdAt.desc())
                .fetch();

        return result.stream()
                .map(r -> {
                    boolean surveysExist = queryFactory
                            .selectOne()
                            .from(survey)
                            .where(survey.memberRecord.record.id.eq(r.getId()))
                            .fetchFirst() != null;

                    return RecordResponse.builder()
                            .recordId(r.getId())
                            .programId(r.getProgram().getId())
                            .centerId(r.getCenter().getId())
                            .startTime(r.getStartedAt())
                            .endTime(r.getFinishedAt())
                            .participantCount(r.getParticipantCount())
                            .surveysExist(surveysExist)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public RecordResponse findRecordById(Long recordId) {
        QRecord records = QRecord.record;
        QProgram program = QProgram.program;

        Record r = queryFactory
                .selectFrom(records)
                .leftJoin(records.program, program).fetchJoin()
                .where(records.id.eq(recordId))
                .fetchOne();

        if (r == null) return null;

        boolean surveysExist = queryFactory
                .selectOne()
                .from(survey)
                .where(survey.memberRecord.record.id.eq(r.getId()))
                .fetchFirst() != null;

        return RecordResponse.builder()
                .recordId(r.getId())
                .programId(r.getProgram().getId())
                .centerId(r.getCenter().getId())
                .startTime(r.getStartedAt())
                .endTime(r.getFinishedAt())
                .participantCount(r.getParticipantCount())
                .surveysExist(surveysExist)
                .build();
    }
}

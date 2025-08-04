package com.senifit.was.repository.record;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.entity.QPrograms;
import com.senifit.was.entity.QRecords;
import com.senifit.was.entity.Records;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.senifit.was.entity.QSurveys.surveys;

@Repository
@RequiredArgsConstructor
public class RecordsRepositoryImpl implements RecordsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecordResponse> findAllRecordByCenterId(Long centerId) {
        QRecords records = QRecords.records;
        QPrograms programs = QPrograms.programs;

        // records + programs fetch
        List<Records> result = queryFactory
                .selectFrom(records)
                .leftJoin(records.programs, programs).fetchJoin()
                .where(records.centers.centerId.eq(centerId))
                .orderBy(records.createdAt.desc())
                .fetch();

        return result.stream()
                .map(r -> {
                    boolean surveysExist = queryFactory
                            .selectOne()
                            .from(surveys)
                            .where(surveys.recordsMembers.records.recordId.eq(r.getRecordId()))
                            .fetchFirst() != null;

                    return RecordResponse.builder()
                            .recordId(r.getRecordId())
                            .programId(r.getPrograms().getProgramId())
                            .centerId(r.getCenters().getCenterId())
                            .startTime(r.getStartTime())
                            .endTime(r.getEndTime())
                            .participantCount(r.getParticipantCount())
                            .exerciseTimes(r.getExerciseTimes())
                            .tools(r.getTools())
                            .surveysExist(surveysExist)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public RecordResponse findRecordById(Long recordId) {
        QRecords records = QRecords.records;
        QPrograms programs = QPrograms.programs;

        Records r = queryFactory
                .selectFrom(records)
                .leftJoin(records.programs, programs).fetchJoin()
                .where(records.recordId.eq(recordId))
                .fetchOne();

        if (r == null) return null;

        boolean surveysExist = queryFactory
                .selectOne()
                .from(surveys)
                .where(surveys.recordsMembers.records.recordId.eq(r.getRecordId()))
                .fetchFirst() != null;

        return RecordResponse.builder()
                .recordId(r.getRecordId())
                .programId(r.getPrograms().getProgramId())
                .centerId(r.getCenters().getCenterId())
                .startTime(r.getStartTime())
                .endTime(r.getEndTime())
                .participantCount(r.getParticipantCount())
                .exerciseTimes(r.getExerciseTimes())
                .tools(r.getTools())
                .surveysExist(surveysExist)
                .build();
    }
}

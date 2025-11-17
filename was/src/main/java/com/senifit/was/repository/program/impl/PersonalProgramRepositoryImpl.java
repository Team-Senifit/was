package com.senifit.was.repository.program.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.entity.Program;
import com.senifit.was.entity.QProgram;
import com.senifit.was.entity.lookup.LookupSpecializedWorkoutKind;
import com.senifit.was.entity.selections.SpecializedWorkoutKind;
import com.senifit.was.repository.program.api.PersonalProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PersonalProgramRepositoryImpl implements PersonalProgramRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Program getPersonalByHash(String hash) {
        QProgram q = QProgram.program;
        return queryFactory
                .selectFrom(q)
                .where(q.hash.eq(hash))
                .where(q.specializedWorkoutKind.eq(
                        LookupSpecializedWorkoutKind.fromSelection(
                                SpecializedWorkoutKind.workout_notSelected)))
                .fetchOne();
    }
}

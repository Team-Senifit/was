package com.senifit.was.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.entity.Members;
import com.senifit.was.entity.QMembers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MembersRepositoryImpl implements MembersRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Members> findTop5ByCenterIdOrderByCreatedAtDesc(Long centerId) {
        QMembers members = QMembers.members;

        return queryFactory
                .selectFrom(members)
                .where(members.centers.centerId.eq(centerId))
                .orderBy(members.createdAt.desc())
                .limit(5)
                .fetch();
    }
}

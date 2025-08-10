package com.senifit.was.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.entity.QMember;
import com.senifit.was.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MembersRepositoryImpl implements MembersRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findTop5ByCenterIdOrderByCreatedAtDesc(Long centerId) {
        QMember members = QMember.member;

        return queryFactory
                .selectFrom(members)
                .where(members.center.centerId.eq(centerId))
                .orderBy(members.createdAt.desc())
                .limit(5)
                .fetch();
    }
}

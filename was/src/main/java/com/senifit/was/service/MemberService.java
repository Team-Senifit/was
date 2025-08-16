package com.senifit.was.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.request.member.MemberRequest;
import com.senifit.was.dto.response.member.MemberResponse;
import com.senifit.was.entity.Center;
import com.senifit.was.entity.Member;
import com.senifit.was.entity.QMember;
import com.senifit.was.exception.custom.CenterNotFoundException;
import com.senifit.was.exception.custom.MemberNotFoundException;
import com.senifit.was.repository.center.CentersRepository;
import com.senifit.was.repository.lookup.LookupGenderRepository;
import com.senifit.was.repository.lookup.LookupRankRepository;
import com.senifit.was.repository.member.MembersRepository;
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
public class MemberService {

    private final CentersRepository centersRepository;
    private final MembersRepository membersRepository;
    private final JPAQueryFactory queryFactory;
    private final LookupGenderRepository lookupGenderRepository;
    private final LookupRankRepository lookupRankRepository;

    /**
     * 센터 회원 목록 조회
     */
    public List<MemberResponse> getMembersByCenterId(Long centerId) {
        QMember members = QMember.member;

        List<Member> memberList = queryFactory
                .selectFrom(members)
                .where(members.center.centerId.eq(centerId))
                .orderBy(members.createdAt.desc())
                .fetch();

        return memberList.stream()
                .map(m -> MemberResponse.builder()
                        .memberId(m.getMemberId())
                        .name(m.getName())
                        .birthDate(m.getBirthDate())
                        .isSolar(m.getIsSolar())
                        .gender(m.getGender().getId())
                        .memberRank(m.getRank().getId())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 센터 회원 상세 조회
     */
    public MemberResponse getMemberById(Long centerId, Long memberId) {
        QMember members = QMember.member;

        Member member = queryFactory
                .selectFrom(members)
                .where(
                        members.memberId.eq(memberId),
                        members.center.centerId.eq(centerId)
                )
                .fetchOne();

        if (member == null) {
            throw new MemberNotFoundException();
        }

        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .birthDate(member.getBirthDate())
                .isSolar(member.getIsSolar())
                .gender(member.getGender().getId())
                .memberRank(member.getRank().getId())
                .build();
    }

    /**
     * 회원 추가
     */
    @Transactional
    public void addMember(MemberRequest request, Long centerId) {
        Center center = centersRepository.findById(centerId)
                .orElseThrow(CenterNotFoundException::new);

        Member member = Member.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .isSolar(request.getIsSolar())
                .gender(lookupGenderRepository.getReferenceById(request.getGender()))
                .rank(lookupRankRepository.getReferenceById(request.getMemberRank()))
                .center(center)
                .build();

        membersRepository.save(member);
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void updateMemberById(MemberRequest request, Long memberId, Long centerId) {
        Member member = membersRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.updateMember(request.getName(),
                request.getBirthDate(),
                request.getIsSolar(),
                lookupGenderRepository.getReferenceById(request.getGender()),
                lookupRankRepository.getReferenceById(request.getMemberRank()));
    }

    @Transactional
    public void deleteMemberById(Long memberId, Long centerId) {
        QMember qmember = QMember.member;

        Member member = queryFactory
                .selectFrom(qmember)
                .where(
                        qmember.memberId.eq(memberId),
                        qmember.center.centerId.eq(centerId)
                )
                .fetchOne();

        if (member == null) {
            throw new MemberNotFoundException();
        }

        membersRepository.delete(member);
    }
}

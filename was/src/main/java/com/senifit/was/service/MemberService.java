package com.senifit.was.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.request.member.MemberRequest;
import com.senifit.was.dto.response.member.MemberResponse;
import com.senifit.was.entity.Centers;
import com.senifit.was.entity.Members;
import com.senifit.was.entity.QMembers;
import com.senifit.was.exception.custom.CenterNotFoundException;
import com.senifit.was.exception.custom.MemberNotFoundException;
import com.senifit.was.repository.center.CentersRepository;
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

    /**
     * 센터 회원 목록 조회
     */
    public List<MemberResponse> getMembersByCenterId(Long centerId) {
        QMembers members = QMembers.members;

        List<Members> memberList = queryFactory
                .selectFrom(members)
                .where(members.centers.centerId.eq(centerId))
                .orderBy(members.createdAt.desc())
                .fetch();

        return memberList.stream()
                .map(m -> MemberResponse.builder()
                        .memberId(m.getMemberId())
                        .name(m.getName())
                        .age(m.getAge())
                        .gender(m.getGender())
                        .memberRank(m.getMemberRank())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 센터 회원 상세 조회
     */
    public MemberResponse getMemberById(Long centerId, Long memberId) {
        QMembers members = QMembers.members;

        Members member = queryFactory
                .selectFrom(members)
                .where(
                        members.memberId.eq(memberId),
                        members.centers.centerId.eq(centerId)
                )
                .fetchOne();

        if (member == null) {
            throw new MemberNotFoundException();
        }

        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .age(member.getAge())
                .gender(member.getGender())
                .memberRank(member.getMemberRank())
                .build();
    }

    /**
     * 회원 추가
     */
    @Transactional
    public Long addMember(MemberRequest request, Long centerId) {
        Centers center = centersRepository.findById(centerId)
                .orElseThrow(CenterNotFoundException::new);

        Members member = Members.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .gender(request.getGender())
                .memberRank(request.getMemberRank())
                .centers(center)
                .build();

        membersRepository.save(member);
        return member.getMemberId();
    }

    /**
     * 회원 수정
     */
    @Transactional
    public Long updateMemberById(MemberRequest request, Long memberId, Long centerId) {
        Members member = membersRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.updateMembers(request.getName(), request.getBirthDate(), request.getGender(), request.getMemberRank());

        return member.getMemberId();
    }

    @Transactional
    public Long deleteMemberById(Long memberId, Long centerId) {
        QMembers members = QMembers.members;

        Members member = queryFactory
                .selectFrom(members)
                .where(
                        members.memberId.eq(memberId),
                        members.centers.centerId.eq(centerId)
                )
                .fetchOne();

        if (member == null) {
            throw new MemberNotFoundException();
        }

        membersRepository.delete(member);
        return memberId;
    }
}

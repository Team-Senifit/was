package com.senifit.was.service;

import com.senifit.was.dto.request.center.CenterUpdateRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.center.CenterResponse;
import com.senifit.was.dto.response.member.MemberResponse;
import com.senifit.was.entity.Centers;
import com.senifit.was.entity.Members;
import com.senifit.was.exception.custom.CenterNotFoundException;
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
public class CenterService {
    private final CentersRepository centersRepository;
    private final MembersRepository membersRepository;

    /**
     * Center 조회
     */
    public ApiResponse<CenterResponse> getCenterById(Long centerId) {
        Centers center = centersRepository.findById(centerId)
                .orElseThrow(CenterNotFoundException::new);

        List<Members> top5Members = membersRepository.findTop5ByCenterIdOrderByCreatedAtDesc(centerId);

        List<MemberResponse> memberResponses = top5Members.stream()
                .map(m -> MemberResponse.builder()
                        .memberId(m.getMemberId())
                        .name(m.getName())
                        .age(m.getAge())
                        .gender(m.getGender())
                        .memberRank(m.getMemberRank())
                        .build())
                .collect(Collectors.toList());

        CenterResponse response = CenterResponse.builder()
                .name(center.getName())
                .location(center.getLocation())
                .role(center.getRole())
                .memberCount(center.getMembers().size())
                .members(memberResponses)
                .build();

        return ApiResponse.success(response);
    }

    /**
     * Center 정보 수정
     */
    @Transactional
    public Long updateCenterById(CenterUpdateRequest request, Long centerId) {
        Centers center = centersRepository.findById(centerId)
                .orElseThrow(CenterNotFoundException::new);

        center.updateCenters(request.getName(), request.getLocation());

        return center.getCenterId();
    }

}

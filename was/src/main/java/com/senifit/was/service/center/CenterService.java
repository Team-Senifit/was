package com.senifit.was.service.center;

import com.senifit.was.dto.request.center.CenterCreateRequest;
import com.senifit.was.dto.request.center.CenterUpdateRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.center.CenterResponse;
import com.senifit.was.dto.response.member.MemberResponse;
import com.senifit.was.entity.Center;
import com.senifit.was.entity.Member;
import com.senifit.was.exception.custom.CenterNotFoundException;
import com.senifit.was.repository.center.CentersRepository;
import com.senifit.was.repository.member.MembersRepository;
import com.senifit.was.service.center.exception.DuplicateCenterCodeExcpetion;
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
        Center center = centersRepository.findById(centerId)
                .orElseThrow(CenterNotFoundException::new);

        List<Member> top5Members = membersRepository.findTop5ByCenterIdOrderByCreatedAtDesc(centerId);

        List<MemberResponse> memberResponses = top5Members.stream()
                .map(m -> MemberResponse.builder()
                        .memberId(m.getId())
                        .name(m.getName())
                        .age(m.getAge())
                        .gender(m.getGender().getId())
                        .memberRank(m.getRank().getId())
                        .build())
                .collect(Collectors.toList());

        CenterResponse response = CenterResponse.builder()
                .name(center.getName())
                .centerCode(center.getCenterCode())
                .location(center.getLocation())
                .memberCount(center.getMembers().size())
                .members(memberResponses)
                .build();

        return ApiResponse.success(response);
    }

    /**
     * Center 정보 수정
     */
    @Transactional
    public Long updateCenterByCenterCode(CenterUpdateRequest request) {
        Center center = centersRepository.findByCenterCode(request.getCenterCode())
                        .orElseThrow(CenterNotFoundException::new);

        center.setName(center.getName());
        center.setLocation(center.getLocation());
        center.setDescription(center.getDescription());
        return center.getId();
    }

    @Transactional
    public void createCenter(CenterCreateRequest request) {
        if (centersRepository.existsByCenterCode(request.getCenterCode()))
            throw new DuplicateCenterCodeExcpetion();
        Center center = Center.builder()
                .name(request.getName())
                .location(request.getLocation())
                .description(request.getDescription())
                .build();
        centersRepository.save(center);
    }

}

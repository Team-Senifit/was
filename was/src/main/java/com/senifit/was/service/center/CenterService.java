package com.senifit.was.service.center;

import com.senifit.was.dto.request.center.CenterCreateRequest;
import com.senifit.was.dto.request.center.CenterUpdateRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.center.CenterResponse;
import com.senifit.was.dto.response.member.MemberResponse;
import com.senifit.was.entity.Center;
import com.senifit.was.entity.CenterRole;
import com.senifit.was.entity.Member;
import com.senifit.was.exception.custom.CenterNotFoundException;
import com.senifit.was.repository.center.CentersRepository;
import com.senifit.was.repository.member.MembersRepository;
import com.senifit.was.service.auth.exception.SignupValidationIdExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    /**
     * Center 조회
     */
    public ApiResponse<CenterResponse> getCenterById(Long centerId) {
        Center center = centersRepository.findById(centerId)
                .orElseThrow(CenterNotFoundException::new);

        List<Member> top5Members = membersRepository.findTop5ByCenterIdOrderByCreatedAtDesc(centerId);

        List<MemberResponse> memberResponses = top5Members.stream()
                .map(m -> MemberResponse.builder()
                        .memberId(m.getMemberId())
                        .name(m.getName())
                        .age(m.getAge())
                        .gender(m.getGender().getId())
                        .memberRank(m.getRank().getId())
                        .build())
                .collect(Collectors.toList());

        CenterResponse response = CenterResponse.builder()
                .name(center.getName())
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
        Center center = centersRepository.findById(request.getCenterId())
                        .orElseThrow(CenterNotFoundException::new);
        center.updateCenter(center.getName(), center.getLocation());
        return center.getCenterId();
    }

    @Transactional
    public void createCenter(CenterCreateRequest request) {
        if (centersRepository.existsByLoginId(request.getLoginId()))
            throw new SignupValidationIdExistsException();
        Center center = Center.builder()
                .name(request.getName())
                .loginId(request.getLoginId())
                .location(request.getLocation())
                .role(CenterRole.valueOf(request.getRole()))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        centersRepository.save(center);
    }

}

package com.senifit.was.controller;

import com.senifit.was.dto.request.member.MemberRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.member.MemberResponse;
import com.senifit.was.service.MemberService;
import com.senifit.was.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("centers/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping()
    public ApiResponse<List<MemberResponse>> listMembers(HttpSession session) {
        log.debug("LIST MEMBER");
        Long centerId = SessionUtils.getCenterId(session);
        return ApiResponse.success(memberService.getMembersByCenterId(centerId));
    }

    @GetMapping("{memberId}")
    public ApiResponse<MemberResponse> getMemberById(HttpSession session, @PathVariable("memberId") Long memberId) {
        log.debug("GET MEMBER");
        Long centerId = SessionUtils.getCenterId(session);
        return ApiResponse.success(memberService.getMemberById(centerId, memberId));
    }

    @PostMapping
    public ApiResponse<Map<String, Object>> createMemberById(HttpSession session, @Valid @RequestBody MemberRequest request) {
        log.debug("CREATE MEMBER");
        Long centerId = SessionUtils.getCenterId(session);
        return ApiResponse.success(memberService.addMember(request, centerId));
    }

    @PutMapping("{memberId}")
    public ApiResponse<Map<String, Object>> updateMemberById(HttpSession session, @PathVariable("memberId") Long memberId, @Valid @RequestBody MemberRequest request) {
        log.debug("UPDATE MEMBER");
        Long centerId = SessionUtils.getCenterId(session);
        return ApiResponse.success(memberService.updateMemberById(request, memberId, centerId));
    }

    @DeleteMapping("{memberId}")
    public ApiResponse<Map<String, Object>> deleteMemberById(HttpSession session, @PathVariable("memberId") Long memberId) {
        log.debug("DELETE MEMBER");
        Long centerId = SessionUtils.getCenterId(session);
        return ApiResponse.success(memberService.deleteMemberById(memberId, centerId));
    }

}

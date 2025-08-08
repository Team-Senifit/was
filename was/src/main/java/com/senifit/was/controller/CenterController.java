package com.senifit.was.controller;

import com.senifit.was.dto.request.center.CenterCreateRequest;
import com.senifit.was.dto.request.center.CenterUpdateRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.center.CenterResponse;
import com.senifit.was.service.center.CenterService;
import com.senifit.was.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("centers")
@Slf4j
@RequiredArgsConstructor
public class CenterController {
    private final CenterService centerService;

    @GetMapping()
    public ApiResponse<CenterResponse> getCenter(HttpSession session) {
        log.debug("GET CENTER");
        Long centerId = SessionUtils.getUserId(session);
        return centerService.getCenterById(centerId);
    }

    @PutMapping()
    public ApiResponse<Map<String, Object>> updateCenterById(HttpSession session, @Valid @RequestBody CenterUpdateRequest request) {
        log.debug("UPDATE CENTER");
        Long centerId = SessionUtils.getUserId(session);
        return ApiResponse.success(centerService.updateCenterByCenterCode(request));
    }

}

package com.senifit.was.controller;

import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.healthcheck.HealthCheckResponse;
import com.senifit.was.exception.api.common.NoAccessApiException;
import com.senifit.was.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class HealthCheckController {
    @GetMapping
    public ApiResponse<HealthCheckResponse> healthCheck(
            HttpSession httpSession
    ) {
        try {
            Long centerId = SessionUtils.getUserId(httpSession);
            return ApiResponse.success(
                    new HealthCheckResponse(true, centerId != null)
            );
        } catch (NoAccessApiException e) {
            return ApiResponse.success(
                    new HealthCheckResponse(true, false)
            );
        }
    }
}

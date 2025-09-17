package com.senifit.was.controller.program;

import com.senifit.was.dto.request.program.recommendation.ByPersonalRequest;
import com.senifit.was.dto.request.program.recommendation.ByTargetRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.program.ProgramInfoResponse;
import com.senifit.was.dto.response.program.ProgramResponse;
import com.senifit.was.service.workoutData.RecommendationService;
import com.senifit.was.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("programs/recommendation")
@RequiredArgsConstructor
@Slf4j
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("by-personal")
    public ApiResponse<List<ProgramInfoResponse>> byPersonal(
            @RequestBody ByPersonalRequest request,
            HttpSession session
    ){
        Long centerId = SessionUtils.getUserId(session);
        return ApiResponse.success(recommendationService.byPersonal(request));
    }

    @PostMapping("by-target")
    public ApiResponse<List<ProgramInfoResponse>> byTarget(
            @RequestBody ByTargetRequest request,
            HttpSession session
    ){
        Long centerId = SessionUtils.getUserId(session);
        return ApiResponse.success(recommendationService.byTarget(request));
    }

    @GetMapping("by-popular")
    public ApiResponse<List<ProgramInfoResponse>> byPopular(
            @RequestParam(required = false, defaultValue = "2") Integer count,
            HttpSession session
    ) {
        Long centerId = SessionUtils.getUserId(session);
        return ApiResponse.success(recommendationService.byPopular(count));
    }

}

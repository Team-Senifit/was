package com.senifit.was.controller.program;

import com.senifit.was.dto.request.program.recommendation.ByPersonalRequest;
import com.senifit.was.dto.request.program.recommendation.ByTargetRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.program.ProgramInfoResponse;
import com.senifit.was.dto.response.program.ProgramResponse;
import com.senifit.was.service.workoutData.RecommendationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("programs/recommendation")
@RequiredArgsConstructor
@Slf4j
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("by-personal")
    public ApiResponse<List<ProgramInfoResponse>> byPersonal(
            @RequestBody ByPersonalRequest request
    ){
        return ApiResponse.success(recommendationService.byPersonal(request));
    }

    @PostMapping("by-target")
    public ApiResponse<List<ProgramInfoResponse>> byTarget(
            @RequestBody ByTargetRequest request
    ){
        return ApiResponse.success(recommendationService.byTarget(request));
    }

}

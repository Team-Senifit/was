package com.senifit.was.controller;

import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.program.ProgramInfoResponse;
import com.senifit.was.dto.response.ui.HomeUiResponse;
import com.senifit.was.service.workoutData.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeUiController {
    private final RecommendationService recommendationService;

    @GetMapping("/ui/home")
    public ApiResponse<HomeUiResponse> byPopular() {
        return ApiResponse.success(
                HomeUiResponse
                        .builder()
                        .popularRoutineList(
                                recommendationService.byPopular(2))
                        .build()
        );
    }
}

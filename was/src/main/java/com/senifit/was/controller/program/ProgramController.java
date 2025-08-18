package com.senifit.was.controller.program;

import com.senifit.was.dto.request.center.CenterCreateRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.program.ProgramResponse;
import com.senifit.was.service.RecordService;
import com.senifit.was.service.workoutData.RecommendationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("programs")
@RequiredArgsConstructor
@Slf4j
public class ProgramController {
    private final RecommendationService recommendationService;

    @GetMapping("{programId}")
    public ApiResponse<ProgramResponse> getProgram(@PathVariable Long programId) {
       return ApiResponse.success(recommendationService.getProgram(programId));
    }
}

package com.senifit.was.controller.program;

import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.program.ProgramResponse;
import com.senifit.was.service.ProgramService;
import com.senifit.was.service.recommendation.RecommendationService;
import com.senifit.was.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("programs")
@RequiredArgsConstructor
@Slf4j
public class ProgramController {
    private final ProgramService programService;

    @GetMapping("{programId}")
    public ApiResponse<ProgramResponse> getProgram(
            @PathVariable Long programId,
            HttpSession session
            ) {
       Long centerId = SessionUtils.getUserId(session);
       return ApiResponse.success(programService.getProgram(programId));
    }
}

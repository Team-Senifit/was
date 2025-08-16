package com.senifit.was.controller.program;

import com.senifit.was.dto.request.center.CenterCreateRequest;
import com.senifit.was.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("programs")
@RequiredArgsConstructor
@Slf4j
public class ProgramController {
    @PostMapping("signup")
    public ApiResponse<Void> createCenter(@Valid @RequestBody CenterCreateRequest request) {
        log.debug("CREATE CENTER");
        return ApiResponse.success();
    }


}

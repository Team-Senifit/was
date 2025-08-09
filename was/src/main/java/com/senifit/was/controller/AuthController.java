package com.senifit.was.controller;

import com.senifit.was.dto.request.center.CenterCreateRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.service.center.CenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final CenterService centerService;

    @PostMapping("signup")
    public ApiResponse<Void> createCenter(@Valid @RequestBody CenterCreateRequest request) {
        log.debug("CREATE CENTER");
        centerService.createCenter(request);
        return ApiResponse.success();
    }

    
}

package com.senifit.was.controller;

import com.senifit.was.dto.request.record.RecordRequest;
import com.senifit.was.dto.request.record.RecordUpdateRequest;
import com.senifit.was.dto.request.survey.SurveyRequest;
import com.senifit.was.dto.request.survey.SurveyUpdateRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.survey.SurveyResponse;
import com.senifit.was.exception.api.common.BadRequestApiException;
import com.senifit.was.service.SurveyService;
import com.senifit.was.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("records/{recordId}/surveys")
@Slf4j
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping()
    public ApiResponse<List<SurveyResponse>> listSurvey(@PathVariable("recordId") Long recordId, HttpSession session) {
        log.debug("LIST SURVEY");
        Long centerId = SessionUtils.getCenterId(session);
        return ApiResponse.success(surveyService.getSurveysByRecordId(recordId, centerId));
    }

//    @GetMapping("{surveyId}")
//    public ApiResponse<SurveyResponse> getSurveyById(HttpSession session, @PathVariable("surveyId") Long surveyId) {
//        Long centerId = (Long) session.getAttribute("centerId");
//        if (centerId == null) {
//            throw new BadRequestApiException();
//        }
//        return ApiResponse.success(surveyService.getSurveyById(surveyId));
//    }

    @PostMapping
    public ApiResponse<Map<String, Object>> createSurveyById(HttpSession session, @PathVariable("recordId") Long recordId, @Valid @RequestBody List<SurveyRequest> request) {
        log.debug("CREATE SURVEY");
        Long centerId = SessionUtils.getCenterId(session);
        return ApiResponse.success(surveyService.addSurvey(request, recordId, centerId));
    }

    @PutMapping()
    public ApiResponse<Map<String, Object>> updateSurveyById(HttpSession session, @PathVariable("recordId") Long recordId, @Valid @RequestBody List<SurveyUpdateRequest> request) {
        log.debug("UPDATE SURVEY");
        Long centerId = SessionUtils.getCenterId(session);
        return ApiResponse.success(surveyService.updateSurveyById(request, recordId, centerId));
    }

//    @DeleteMapping("{surveyId}")
//    public ApiResponse<Map<String, Object>> deleteSurveyById(@PathVariable Long recordId, HttpSession session) {
//        Long centerId = (Long) session.getAttribute("centerId");
//        if (centerId == null) {
//            throw new BadRequestApiException();
//        }
//        return ApiResponse.success(surveyService.deleteSurveyById(recordId, centerId));
//    }

}
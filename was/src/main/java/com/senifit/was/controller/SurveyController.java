package com.senifit.was.controller;

import com.senifit.was.dto.request.survey.SurveyRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.record.RecordSurveyResponse;
import com.senifit.was.dto.response.survey.SurveyResponse;
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
    public ApiResponse<RecordSurveyResponse> listSurvey(@PathVariable("recordId") Long recordId, HttpSession session) {
        log.debug("LIST SURVEY");
        return ApiResponse.success(surveyService.getSurveysByRecordId(recordId, SessionUtils.getUserId(session)));
    }

//    @GetMapping("{surveyId}")
//    public ApiResponse<SurveyResponse> getSurveyById(HttpSession session, @PathVariable("surveyId") Long surveyId) {
//        Long centerId = (Long) session.getAttribute("centerId");
//        if (centerId == null) {
//            throw new BadRequestApiException();
//        }
//        return ApiResponse.success(surveyService.getSurveyById(surveyId));
//    }

//    @PostMapping
//    public ApiResponse<Void> createSurveyById(HttpSession session, @PathVariable("recordId") Long recordId, @Valid @RequestBody List<SurveyRequest> request) {
//        log.debug("CREATE SURVEY START");
//        surveyService.addSurvey(request, recordId, SessionUtils.getUserId(session));
//        log.debug("CREATE SURVEY FINISH");
//        return ApiResponse.success();
//    }

    @PutMapping()
    public ApiResponse<Void> updateSurveyById(HttpSession session, @PathVariable("recordId") Long recordId, @Valid @RequestBody List<SurveyRequest> request) {
        log.debug("UPDATE SURVEY START");
        surveyService.updateSurveyById(request, recordId, SessionUtils.getUserId(session));
        log.debug("UPDATE SURVEY FINISH");
        return ApiResponse.success();
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
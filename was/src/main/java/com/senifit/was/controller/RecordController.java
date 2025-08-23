package com.senifit.was.controller;

import com.senifit.was.dto.request.record.RecordRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.service.RecordService;
import com.senifit.was.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("records")
@Slf4j
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ApiResponse<List<RecordResponse>> listRecord(HttpSession session) {
        log.debug("LIST RECORD");
        return ApiResponse.success(recordService.getRecordsByCenterId(SessionUtils.getUserId(session)));
    }

//    @GetMapping("{recordId}")
//    public ApiResponse<RecordResponse> getRecordById(HttpSession session, @PathVariable Long recordId) {
//        log.debug("RECORD ID: {}", recordId);
//        return ApiResponse.success(recordService.getRecordById(SessionUtils.getUserId(session), recordId));
//    }

    @PostMapping
    public ApiResponse<Map<String, Object>> createRecordById(HttpSession session, @Valid @RequestBody RecordRequest request) {
        log.debug("CREATE RECORD START");
        Long recordId = recordService.addRecord(request, SessionUtils.getUserId(session));
        log.debug("CREATE RECORD FINISH - Record ID: {}", recordId);
        return ApiResponse.success(recordId);
    }

//    @PutMapping("{recordId}")
//    public ApiResponse<Map<String, Object>> updateRecordById(HttpSession session, @PathVariable Long recordId, @Valid @RequestBody RecordUpdateRequest request) {
//        Long centerId = (Long) session.getAttribute("centerId");
//        if (centerId == null) {
//            throw new BadRequestApiException();
//        }
//        return ApiResponse.success(recordService.updateRecordById(recordId, request, centerId));
//    }

    @PutMapping("{recordId}")
    public ApiResponse<Void> updateRecordFinishAtById(@PathVariable Long recordId, HttpSession session) {
        log.debug("FINISH RECORD START - Record ID: {}", recordId);
        recordService.updateRecordFinishAt(recordId, SessionUtils.getUserId(session));
        log.debug("FINISH RECORD FINISH - Record ID: {}", recordId);
        return ApiResponse.success();
    }

    @DeleteMapping("{recordId}")
    public ApiResponse<Void> deleteRecordById(@PathVariable Long recordId, HttpSession session) {
        log.debug("DELETE RECORD START - Record ID: {}", recordId);
        recordService.deleteRecordById(recordId, SessionUtils.getUserId(session));
        log.debug("DELETE RECORD FINISH - Record ID: {}", recordId);
        return ApiResponse.success();
    }


}

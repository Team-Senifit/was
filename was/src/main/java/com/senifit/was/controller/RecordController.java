package com.senifit.was.controller;

import com.senifit.was.dto.request.record.RecordRequest;
import com.senifit.was.dto.request.record.RecordUpdateRequest;
import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.exception.api.common.BadRequestApiException;
import com.senifit.was.service.RecordService;
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
        Long centerId = (Long) session.getAttribute("centerId");
        if (centerId == null) {
            throw new BadRequestApiException();
        }
        return ApiResponse.success(recordService.getRecordsByCenterId(centerId));
    }

//    @GetMapping("{recordId}")
//    public ApiResponse<RecordResponse> getRecordById(HttpSession session, @PathVariable Long recordId) {
//        Long centerId = (Long) session.getAttribute("centerId");
//        if (centerId == null) {
//            throw new BadRequestApiException();
//        }
//            return ApiResponse.success(recordService.getRecordById(recordId));
//    }

    @PostMapping
    public ApiResponse<Map<String, Object>> createRecordById(HttpSession session, @Valid @RequestBody RecordRequest request) {
        Long centerId = (Long) session.getAttribute("centerId");
        if (centerId == null) {
            throw new BadRequestApiException();
        }
        return ApiResponse.success(recordService.addRecord(request, centerId));
    }

//    @PutMapping("{recordId}")
//    public ApiResponse<Map<String, Object>> updateRecordById(HttpSession session, @PathVariable Long recordId, @Valid @RequestBody RecordUpdateRequest request) {
//        Long centerId = (Long) session.getAttribute("centerId");
//        if (centerId == null) {
//            throw new BadRequestApiException();
//        }
//        return ApiResponse.success(recordService.updateRecordById(recordId, request, centerId));
//    }

    @DeleteMapping("{recordId}")
    public ApiResponse<Map<String, Object>> deleteRecordById(@PathVariable Long recordId, HttpSession session) {
        Long centerId = (Long) session.getAttribute("centerId");
        if (centerId == null) {
            throw new BadRequestApiException();
        }
        return ApiResponse.success(recordService.deleteRecordById(recordId, centerId));
    }


}

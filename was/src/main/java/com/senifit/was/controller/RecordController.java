package com.senifit.was.controller;

import com.senifit.was.common.response.ApiResponse;
import com.senifit.was.dto.request.record.RecordRequest;
import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.exception.custom.UserNotFoundException;
import com.senifit.was.service.RecordService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("records")
@Slf4j
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ApiResponse<List<RecordResponse>> listRecord(HttpSession session) {
        Long centerId = (Long) session.getAttribute("centerId");
        if (centerId == null) {
            throw new UserNotFoundException();
        }

        List<RecordResponse> records = recordService.getRecordsByCenterId(centerId);
        return new ApiResponse<>(records);
    }

    @GetMapping("{recordId}")
    public ApiResponse<Record> getRecordById(@PathVariable Long recordId) {
        return null;
    }

    @PostMapping
    public ApiResponse<Long> addRecordById() {
        return null;
    }

    @PutMapping("{recordId}")
    public ApiResponse<Long> updateRecordById(@PathVariable Long recordId, @Valid @RequestBody RecordRequest request) {
        return null;
    }

    @DeleteMapping("/{recordId}")
    public ApiResponse<Long> deleteRecordById(@PathVariable Long recordId) {
        return null;
    }


}

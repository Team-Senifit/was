package com.senifit.was.controller;

import com.senifit.was.common.response.ApiResponse;
import com.senifit.was.dto.request.record.RecordRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("records")
@Slf4j
public class RecordController {

    @GetMapping
    public ApiResponse<List<Record>> listRecord() {
        return null;
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

package com.senifit.was.dto.response.ui;

import com.senifit.was.dto.response.program.ProgramInfoResponse;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeUiResponse {
    public List<ProgramInfoResponse> popularRoutineList;
}

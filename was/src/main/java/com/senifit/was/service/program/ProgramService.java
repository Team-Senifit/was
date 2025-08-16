package com.senifit.was.service.program;

import com.senifit.was.entity.Bundle;
import com.senifit.was.entity.Program;
import com.senifit.was.entity.ProgramBundle;
import com.senifit.was.entity.lookup.LookupTarget;
import com.senifit.was.entity.lookup.LookupWorkoutCognitiveKind;
import com.senifit.was.entity.lookup.LookupWorkoutSingingKind;
import com.senifit.was.entity.selections.CognitiveWorkoutKind;
import com.senifit.was.entity.selections.IncludesSingingWorkout;
import com.senifit.was.entity.selections.TargetKind;
import com.senifit.was.repository.program.ProgramRepository;
import com.senifit.was.service.ParseXlsxService;
import com.senifit.was.service.program.exception.InvalidXlsxTemplateApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProgramService {
}

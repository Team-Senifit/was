package com.senifit.was.service;

import com.senifit.was.dto.request.survey.SurveyRequest;
import com.senifit.was.dto.request.survey.SurveyUpdateRequest;
import com.senifit.was.dto.response.survey.SurveyResponse;
import com.senifit.was.dto.response.survey.TroublePartsResponse;
import com.senifit.was.entity.RecordsMembers;
import com.senifit.was.entity.Surveys;
import com.senifit.was.entity.TroubleParts;
import com.senifit.was.exception.custom.SurveyNotFoundException;
import com.senifit.was.exception.custom.MemberNotFoundException;
import com.senifit.was.repository.record.RecordsMembersRepository;
import com.senifit.was.repository.survey.SurveysRepository;
import com.senifit.was.repository.survey.TroublePartsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyService {

    private final SurveysRepository surveysRepository;
    private final RecordsMembersRepository recordsMembersRepository;
    private final TroublePartsRepository troublePartsRepository;

    public List<SurveyResponse> getSurveysByRecordId(Long recordId, Long centerId) {
        return surveysRepository.findSurveyResponsesByRecordIdAndCenterId(recordId, centerId);
    }

    public SurveyResponse getSurveyById(Long surveyId) {
        Surveys survey = surveysRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);

        List<TroublePartsResponse> troublePartsResponses = survey.getTroubleParts().stream()
                .map(tp -> TroublePartsResponse.builder()
                        .muscleType1(tp.getMuscleType1())
                        .build())
                .toList();

        return SurveyResponse.builder()
                .surveyId(survey.getSurveyId())
                .troubleParts(troublePartsResponses)
                .attitude(survey.getAttitude())
                .ability(survey.getAbility())
                .trouble(survey.isTrouble())
                .centerId(survey.getCenterId())
                .updatedAt(survey.getUpdatedAt())
                .build();
    }

    @Transactional
    public Long addSurvey(List<SurveyRequest> request, Long recordId, Long centerId) {
        RecordsMembers recordsMember = recordsMembersRepository
                .findByRecords_RecordIdAndMembers_Centers_CenterId(recordId, centerId)
                .orElseThrow(MemberNotFoundException::new);

        List<Surveys> surveyList = new ArrayList<>();

        for (SurveyRequest req : request) {
            Surveys survey = Surveys.builder()
                    .recordsMembers(recordsMember)
                    .attitude(req.getAttitude())
                    .ability(req.getAbility())
                    .trouble(req.isTrouble())
                    .centerId(centerId)
                    .build();

            List<TroubleParts> troubleParts = req.getTroubleParts().stream()
                    .map(tp -> new TroubleParts(tp.getMuscleType1(), survey))
                    .toList();

            survey.getTroubleParts().addAll(troubleParts);
            surveyList.add(surveysRepository.save(survey));
        }

        surveysRepository.saveAll(surveyList);

        return (long) surveyList.size();
    }

    @Transactional
    public Long updateSurveyById(List<SurveyUpdateRequest> request, Long recordId, Long centerId) {

        // 1. RecordsMembers 조회
        RecordsMembers recordsMembers = recordsMembersRepository
                .findByRecords_RecordIdAndMembers_Centers_CenterId(recordId, centerId)
                .orElseThrow(MemberNotFoundException::new);

        // 2. Survey 목록 조회
        List<Surveys> surveys = surveysRepository.findByRecordsMembers(recordsMembers);
        if (surveys.size() != request.size()) {
            throw new IllegalArgumentException("요청과 일치하는 설문 개수가 다릅니다.");
        }

        List<Surveys> updatedSurveys = new ArrayList<>();

        for (int i = 0; i < surveys.size(); i++) {
            Surveys survey = surveys.get(i);
            SurveyUpdateRequest req = request.get(i);

            // 기존 TroubleParts 삭제
            troublePartsRepository.deleteAll(survey.getTroubleParts());
            survey.getTroubleParts().clear();

            // TroubleParts 생성 (Builder 사용)
            List<TroubleParts> troubleParts = req.getTroubleParts().stream()
                    .map(tp -> TroubleParts.builder()
                            .muscleType1(tp.getMuscleType1())
                            .surveys(survey)
                            .build())
                    .toList();

            survey.updateSurvey(
                    req.getAttitude(),
                    req.getAbility(),
                    req.isTrouble(),
                    centerId,
                    troubleParts
            );

            updatedSurveys.add(survey);
        }

        // 3. 일괄 저장
        surveysRepository.saveAll(updatedSurveys);

        // 4. 반영된 개수 반환
        return (long) updatedSurveys.size();
    }

//    public Map<String, Object> deleteSurveyById(Long surveyId, Long centerId) {
//        return null;
//    }
}

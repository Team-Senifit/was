package com.senifit.was.service;

import com.senifit.was.dto.request.survey.SurveyRequest;
import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.dto.response.survey.SurveyResponse;
import com.senifit.was.dto.response.survey.TroublePartsResponse;
import com.senifit.was.entity.MemberRecord;
import com.senifit.was.entity.Survey;
import com.senifit.was.entity.SurveyTroublePart;
import com.senifit.was.exception.custom.RecordNotFoundException;
import com.senifit.was.exception.custom.SurveyNotFoundException;
import com.senifit.was.exception.custom.MemberNotFoundException;
import com.senifit.was.exception.custom.SurveySizeMismatchException;
import com.senifit.was.repository.lookup.LookupTargetRepository;
import com.senifit.was.repository.record.RecordsMembersRepository;
import com.senifit.was.repository.survey.SurveysRepository;
import com.senifit.was.repository.survey.TroublePartsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyService {

    private final RecordService recordService;
    private final SurveysRepository surveysRepository;
    private final RecordsMembersRepository memberRecordRepository;
    private final TroublePartsRepository troublePartsRepository;
    private final LookupTargetRepository lookupTargetRepository;

    public List<SurveyResponse> getSurveysByRecordId(Long recordId, Long centerId) {

        RecordResponse record = recordService.getRecordById(recordId);

        // record가 없거나 centerId가 다를 시, 예외 처리
        if (record == null || (record.getCenterId() != null && !record.getCenterId().equals(centerId))) {
            throw new RecordNotFoundException();
        }

        return surveysRepository.findAllSurveyByRecordIdAndCenterId(recordId, centerId);
    }

    public SurveyResponse getSurveyById(Long surveyId) {
        Survey survey = surveysRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);

        List<TroublePartsResponse> troublePartsResponses = survey.getSurveyTroubleParts().stream()
                .map(tp -> TroublePartsResponse.builder()
                        .target(tp.getTarget().getId())
                        .build())
                .toList();

        return SurveyResponse.builder()
                .surveyId(survey.getSurveyId())
                .troubleParts(troublePartsResponses)
                .attitudeScore(survey.getAttitudeScore())
                .abilityScore(survey.getAbilityScore())
                .hadTrouble(survey.getHadTrouble())
                .updatedAt(survey.getUpdatedAt())
                .build();
    }

    @Transactional
    public void addSurvey(List<SurveyRequest> request, Long recordId, Long centerId) {
        MemberRecord memberRecord = memberRecordRepository
                .findByRecord_RecordIdAndMember_Center_CenterId(recordId, centerId)
                .orElseThrow(MemberNotFoundException::new);

        List<Survey> surveyList = new ArrayList<>();

        for (SurveyRequest req : request) {
            Survey survey = Survey.builder()
                    .memberRecord(memberRecord)
                    .attitudeScore(req.getAttitudeScore())
                    .abilityScore(req.getAbilityScore())
                    .hadTrouble(req.isHadTrouble())
                    .centerId(centerId)
                    .build();

            List<SurveyTroublePart> targets = req.getTroubleParts().stream()
                    .map(tp ->
                            new SurveyTroublePart(
                                survey,
                                lookupTargetRepository.getReferenceById(tp.getId())
                            )
                        )
                    .toList();

            survey.getSurveyTroubleParts().addAll(targets);
            surveyList.add(surveysRepository.save(survey));
        }

        surveysRepository.saveAll(surveyList);
    }

    @Transactional
    public void updateSurveyById(List<SurveyRequest> request, Long recordId, Long centerId) {

        // 1. RecordsMembers 조회
        MemberRecord memberRecord = memberRecordRepository
                .findByRecord_RecordIdAndMember_Center_CenterId(recordId, centerId)
                .orElseThrow(MemberNotFoundException::new);

        // 2. Survey 목록 조회
        List<Survey> surveys = surveysRepository.findByMemberRecord(memberRecord);
        if (surveys.size() != request.size()) {
            throw new SurveySizeMismatchException();
        }

        List<Survey> updatedSurveys = new ArrayList<>();

        for (int i = 0; i < surveys.size(); i++) {
            Survey survey = surveys.get(i);
            SurveyRequest req = request.get(i);

            // 기존 TroubleParts 삭제
            troublePartsRepository.deleteAll(survey.getSurveyTroubleParts());
            survey.getSurveyTroubleParts().clear();

            // TroubleParts 생성 (Builder 사용)
            List<SurveyTroublePart> troubleParts = req.getTroubleParts().stream()
                    .map(tp -> SurveyTroublePart.builder()
                            .target(lookupTargetRepository.getReferenceById(tp.getId()))
                            .survey(survey)
                            .build())
                    .toList();


            survey.updateSurvey(
                    req.getAttitudeScore(),
                    req.getAbilityScore(),
                    req.isHadTrouble(),
                    centerId,
                    troubleParts
            );

            updatedSurveys.add(survey);
        }

        // 3. 일괄 저장
        surveysRepository.saveAll(updatedSurveys);
    }

//    public Map<String, Object> deleteSurveyById(Long surveyId, Long centerId) {
//        return null;
//    }
}

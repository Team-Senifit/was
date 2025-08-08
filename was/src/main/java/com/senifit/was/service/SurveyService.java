package com.senifit.was.service;

import com.senifit.was.dto.request.survey.SurveyCreateRequest;
import com.senifit.was.dto.request.survey.SurveyUpdateRequest;
import com.senifit.was.dto.response.survey.SurveyResponse;
import com.senifit.was.dto.response.survey.TroublePartsResponse;
import com.senifit.was.entity.MemberRecord;
import com.senifit.was.entity.Survey;
import com.senifit.was.entity.SurveyTroublePart;
import com.senifit.was.exception.api.ApiException;
import com.senifit.was.exception.custom.SurveyNotFoundException;
import com.senifit.was.exception.custom.MemberNotFoundException;
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

    private final SurveysRepository surveysRepository;
    private final RecordsMembersRepository memberRecordRepository;
    private final TroublePartsRepository troublePartsRepository;
    private final LookupTargetRepository lookupTargetRepository;

    public List<SurveyResponse> getSurveysByRecordId(Long recordId, Long centerId) {
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
                .surveyId(survey.getId())
                .troubleParts(troublePartsResponses)
                .attitude(survey.getAttitudeScore())
                .ability(survey.getAbilityScore())
                .trouble(survey.getHadTrouble())
                .centerId(survey.getCenterId())
                .updatedAt(survey.getUpdatedAt())
                .build();
    }

    @Transactional
    public Long addSurvey(List<SurveyCreateRequest> request, Long recordId, Long centerId) {
        MemberRecord memberRecord = memberRecordRepository
                .findByRecord_IdAndMember_Center_Id(recordId, centerId)
                .orElseThrow(MemberNotFoundException::new);

        List<Survey> surveyList = new ArrayList<>();

        for (SurveyCreateRequest req : request) {
            Survey survey = Survey.builder()
                    .memberRecord(memberRecord)
                    .attitudeScore(req.getAttitude())
                    .abilityScore(req.getAbility())
                    .hadTrouble(req.isTrouble())
                    .centerId(centerId)
                    .build();

            List<SurveyTroublePart> targets = req.getTroubleParts().stream()
                    .map(tp ->
                            new SurveyTroublePart(
                                survey,
                                lookupTargetRepository.getReferenceById(Long.valueOf(tp))
                            )
                        )
                    .toList();

            survey.getSurveyTroubleParts().addAll(targets);
            surveyList.add(surveysRepository.save(survey));
        }

        surveysRepository.saveAll(surveyList);

        return (long) surveyList.size();
    }

    @Transactional
    public Long updateSurveyById(List<SurveyUpdateRequest> request, Long recordId, Long centerId) {

        // 1. RecordsMembers 조회
        MemberRecord memberRecord = memberRecordRepository
                .findByRecord_IdAndMember_Center_Id(recordId, centerId)
                .orElseThrow(MemberNotFoundException::new);

        // 2. Survey 목록 조회
        List<Survey> surveys = surveysRepository.findByMemberRecord(memberRecord);
        if (surveys.size() != request.size()) {
            throw new ApiException("idk", 400, "사이즈가 다릅니다.");
        }

        List<Survey> updatedSurveys = new ArrayList<>();

        for (int i = 0; i < surveys.size(); i++) {
            Survey survey = surveys.get(i);
            SurveyUpdateRequest req = request.get(i);

            // 기존 TroubleParts 삭제
            troublePartsRepository.deleteAll(survey.getSurveyTroubleParts());
            survey.getSurveyTroubleParts().clear();

            // TroubleParts 생성 (Builder 사용)
            List<SurveyTroublePart> troubleParts = req.getTroubleParts().stream()
                    .map(tp -> SurveyTroublePart.builder()
                            .target(lookupTargetRepository.getReferenceById(Long.valueOf(tp)))
                            .survey(survey)
                            .build())
                    .toList();

            survey.setAttitudeScore(req.getAttitude());
            survey.setAbilityScore(req.getAbility());
            survey.setHadTrouble(req.isTrouble());
            survey.setCenterId(centerId);
            survey.setSurveyTroubleParts(troubleParts);
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

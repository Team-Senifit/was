package com.senifit.was.service;

import com.senifit.was.dto.request.survey.SurveyRequest;
import com.senifit.was.dto.response.record.RecordResponse;
import com.senifit.was.dto.response.survey.SurveyResponse;
import com.senifit.was.dto.response.survey.TroublePartsResponse;
import com.senifit.was.entity.Record;
import com.senifit.was.entity.MemberRecord;
import com.senifit.was.entity.Survey;
import com.senifit.was.entity.SurveyTroublePart;
import com.senifit.was.entity.selections.BaseSelectionEnum;
import com.senifit.was.entity.selections.TargetKind;
import com.senifit.was.exception.custom.RecordNotFoundException;
import com.senifit.was.exception.custom.SurveyNotFoundException;
import com.senifit.was.exception.custom.MemberNotFoundException;
import com.senifit.was.exception.custom.SurveySizeMismatchException;
import com.senifit.was.repository.lookup.LookupTargetRepository;
import com.senifit.was.repository.record.RecordsMembersRepository;
import com.senifit.was.repository.record.RecordsRepository;
import com.senifit.was.repository.survey.SurveysRepository;
import com.senifit.was.repository.survey.TroublePartsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyService {

    private final RecordService recordService;
    private final SurveysRepository surveysRepository;
    private final RecordsRepository recordsRepository;
    private final RecordsMembersRepository memberRecordRepository;
    private final TroublePartsRepository troublePartsRepository;
    private final LookupTargetRepository lookupTargetRepository;

    public List<SurveyResponse> getSurveysByRecordId(Long recordId, Long centerId) {

        if (!recordsRepository.existsByRecordIdAndCenter_CenterId(recordId, centerId)) {
            throw new RecordNotFoundException();
        }

        return surveysRepository.findAllSurveyByRecordIdAndCenterId(recordId, centerId);
    }

    public SurveyResponse getSurveyById(Long surveyId) {
        Survey survey = surveysRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);

        List<TroublePartsResponse> troublePartsResponses = survey.getSurveyTroubleParts().stream()
                .map(tp -> TroublePartsResponse.builder()
                        .target(BaseSelectionEnum.fromId(
                                TargetKind.class,
                                tp.getTarget().getId()
                        ))
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

        // recordId-centerId 매칭 검증 (존재+소속)
        Record record = recordsRepository.findByRecordIdAndCenterId(recordId, centerId)
                .orElseThrow(RecordNotFoundException::new);

        // 해당 record의 모든 Survey를 QueryDSL로 조회
        List<Survey> surveys = surveysRepository.findAllByRecordId(record.getRecordId());

        // 빠른 매칭을 위한 Map (surveyId -> Survey)
        Map<Long, Survey> surveyMap = surveys.stream()
                .collect(Collectors.toMap(Survey::getSurveyId, Function.identity()));

        // 요청 id 유효성 검증: 요청된 surveyId 모두가 해당 record 소속인지 확인
        Set<Long> reqIds = request.stream()
                .map(SurveyRequest::getSurveyId)
                .collect(Collectors.toSet());

        Set<Long> missing = new HashSet<>(reqIds);
        missing.removeAll(surveyMap.keySet());
        if (!missing.isEmpty()) {
            throw new SurveyNotFoundException();
        }

        // 기존 TroubleParts를 삭제
        //  - Bulk delete는 영속성 컨텍스트를 우회하므로, 이후 엔티티 컬렉션은 반드시 재세팅해야 함
        troublePartsRepository.deleteBySurveyIds(reqIds);

        // 요청대로 엔티티 업데이트 (updateSurvey는 내부에서 clear() 후 addAll 수행)
        List<Survey> toSave = new ArrayList<>(reqIds.size());

        // 요청의 각 항목을 해당 Survey에 반영
        for (SurveyRequest req : request) {
            Survey survey = surveyMap.get(req.getSurveyId());

            List<SurveyTroublePart> newParts =
                    (req.getTroubleParts() == null || req.getTroubleParts().isEmpty())
                            ? List.of()
                            : req.getTroubleParts().stream()
                            .map(tp -> SurveyTroublePart.builder()
                                    .survey(survey) // 역참조 필수
                                    .target(lookupTargetRepository.getReferenceById(tp.getId()))
                                    .build()
                            ).toList();

            survey.updateSurvey(
                    req.getAttitudeScore(),
                    req.getAbilityScore(),
                    req.isHadTrouble(),
                    centerId,
                    newParts
            );

            toSave.add(survey);
        }

        // 일괄 저장
        surveysRepository.saveAll(toSave);

        if (!record.isSurveyExist()) {
            record.updateSurveyExist();
             recordsRepository.save(record);
        }
    }

//    public Map<String, Object> deleteSurveyById(Long surveyId, Long centerId) {
//        return null;
//    }
}

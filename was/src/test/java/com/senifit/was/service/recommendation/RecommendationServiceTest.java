package com.senifit.was.service.recommendation;

import com.senifit.was.dto.request.program.recommendation.ByPersonalRequest;
import com.senifit.was.dto.response.program.ProgramInfoResponse;
import com.senifit.was.dto.response.program.ProgramResponse;
import com.senifit.was.dto.response.program.VideoResponse;
import com.senifit.was.entity.selections.CognitiveWorkoutKind;
import com.senifit.was.entity.selections.DurationKind;
import com.senifit.was.entity.selections.IncludesSingingWorkout;
import com.senifit.was.entity.selections.TargetKind;
import com.senifit.was.service.ProgramService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class RecommendationServiceTest {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private ProgramService programService;

    @Test
    @DisplayName("byPersonal - 같은 조합으로 200번 반복 시 중복 영상 없는지 확인")
    void testByPersonalNoDuplicateVideosIn200Iterations() {
        // Given: 같은 요청으로 200번 반복
        ByPersonalRequest request = new ByPersonalRequest();
        request.setDuration(DurationKind.workout_duration_60minutes);
        request.setCognitive_workout_code(CognitiveWorkoutKind.workout_kinds_cognitive_kinds_taekwondo);
        request.setPrimary_target_code(TargetKind.workout_kinds_calisthenic_targets_arms);
        request.setSinging_workout_code(IncludesSingingWorkout.workout_notSelected);

        Set<Long> allProgramIds = new HashSet<>();
        int totalPrograms = 0;
        int totalVideos = 0;

        // When: 200번 반복 실행
        for (int i = 0; i < 200; i++) {
            List<ProgramInfoResponse> responses = recommendationService.byPersonal(request);

            // Then: 각 반복마다 검증
            assertThat(responses).hasSize(1);
            ProgramInfoResponse response = responses.get(0);

            Long programId = response.getId();

            // 프로그램 ID가 중복되지 않는지 확인
            assertThat(allProgramIds.add(programId))
                    .withFailMessage("반복 %d에서 중복된 프로그램 ID 발견: %s", i + 1, programId)
                    .isTrue();

            // 프로그램의 상세 정보와 비디오들을 가져와서 검증
            ProgramResponse programDetail = programService.getProgram(programId);

            // 프로그램에 포함된 모든 비디오 ID 수집
            List<Long> programVideoIds = programDetail.getVideos().stream()
                    .map(VideoResponse::getId)
                    .collect(Collectors.toList());

            // 프로그램 내에서 비디오 ID들이 중복되지 않는지 확인 (각 프로그램별로)
            Set<Long> uniqueVideoIdsInProgram = new HashSet<>(programVideoIds);
            assertThat(uniqueVideoIdsInProgram).hasSize(programVideoIds.size())
                    .withFailMessage("반복 %d에서 프로그램 내 비디오 중복 발견 (프로그램 ID: %s)",
                            i + 1, programId);

            totalPrograms++;
            totalVideos += programVideoIds.size();

            System.out.printf("반복 %d: 프로그램 ID = %s, 비디오 수 = %d%n",
                    i + 1, programId, programVideoIds.size());
        }

        // 최종 검증: 총 200개의 고유한 프로그램이 생성되었는지 확인
        assertThat(allProgramIds).hasSize(200);
        assertThat(totalPrograms).isEqualTo(200);

        System.out.printf("총 생성된 고유 프로그램 수: %d%n", allProgramIds.size());
        System.out.printf("총 처리된 프로그램 수: %d%n", totalPrograms);
        System.out.printf("총 처리된 비디오 수: %d%n", totalVideos);
    }

}

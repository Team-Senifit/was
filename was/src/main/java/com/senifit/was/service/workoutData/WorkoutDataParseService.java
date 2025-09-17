package com.senifit.was.service.workoutData;

import com.senifit.was.entity.*;
import com.senifit.was.entity.lookup.*;
import com.senifit.was.entity.selections.*;
import com.senifit.was.service.ParseXlsxService;
import com.senifit.was.service.workoutData.exception.InvalidXlsxTemplateApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WorkoutDataParseService {
    private final ParseXlsxService parseXlsxService;

    void parseProgramAndProgramBundle(
            List<Map<String, Object>> programSheetInput,
            BundleParseVo bundles,
            ProgramBundleListParseVo programBundles,
            ProgramParseVo programs
    ) {
        for (Map<String, Object> row : programSheetInput) {
            Long programId = (Long) row.get("id");
            if (programId == null)
                continue;

            LookupWorkoutCooldownKind cooldownKind =
                    LookupWorkoutCooldownKind.fromSelection(
                            CooldownWorkoutKind.fromCode((String) row.get("cooldown_workout_kind_id")));
            LookupWorkoutWarmupKind warmupKind =
                    LookupWorkoutWarmupKind.fromSelection(
                            WarmupWorkoutKind.fromCode((String) row.get("warmup_workout_kind_id")));
            LookupWorkoutCognitiveKind cognitiveKind =
                    LookupWorkoutCognitiveKind.fromSelection(
                            CognitiveWorkoutKind.fromCode((String) row.get("cognitive_workout_kind_id")));
            LookupWorkoutSingingKind singingKind =
                    LookupWorkoutSingingKind.fromSelection(
                            IncludesSingingWorkout.fromCode((String) row.get("singing_workout_kind_id")));
            LookupTarget targetKind =
                    LookupTarget.fromSelection(
                            TargetKind.fromCode((String) row.get("primary_target_kind_id")));
            LookupSpecializedWorkoutKind specializedWorkoutKind =
                    LookupSpecializedWorkoutKind.fromSelection(
                            SpecializedWorkoutKind.fromCode((String) row.get("specialized_workout_kind_id")));

            Program p = Program.builder()
                    .id(programId)
                    .name((String) row.get("name"))
                    .description((String) row.get("description"))
                    .thumbnailPath((String) row.get("thumbnail_path"))
                    .duration(Math.toIntExact((Long) row.get("duration")))
                    .warmupWorkoutKind(warmupKind)
                    .cooldownWorkoutKind(cooldownKind)
                    .cognitiveWorkoutKind(cognitiveKind)
                    .usedCount((Long) row.get("used_count"))
                    .singingWorkoutKind(singingKind)
                    .specializedWorkoutKind(specializedWorkoutKind)
                    .primaryTarget(targetKind).build();
            programs.put(programId, p);

            for (int i = 1; i <= 15; i++) {
                Object bundleIdRaw = row.get(Integer.toString(i));
                if (bundleIdRaw == null)
                    continue;
                if (bundleIdRaw.equals(""))
                    continue;
                Long bundleId = (Long) bundleIdRaw;
                if (!bundles.containsKey(bundleId))
                    throw new InvalidXlsxTemplateApiException();

                ProgramBundle pb = ProgramBundle.builder()
                        .program(p)
                        .bundle(bundles.get(bundleId))
                        .sequence(i)
                        .build();
                if (!(programBundles.containsKey(p))) {
                    programBundles.put(p, new ProgramBundleParseVo());
                }
                programBundles.get(p).add(pb);
            }

        }
    }

    void parseBundleAndBundleVideo(
        List<Map<String, Object>> bundleSheetInput,
        VideoParseVo videos,
        BundleVideoListParseVo bundleVideos,
        BundleParseVo bundles
    ) {
        for (Map<String, Object> row : bundleSheetInput) {
            Long bundleId = (Long) row.get("id");
            if (bundleId == null)
                continue;

            LookupBundleKind bundleKind =
                    LookupBundleKind.fromSelection(BundleKind.fromCode((String) row.get("bundle_kind_id")));
            if (bundleKind == null)
                throw new InvalidXlsxTemplateApiException();

            Bundle b = Bundle.builder()
                    .id(bundleId)
                    .name("")
                    .kind(bundleKind)
                    .build();
            bundles.put(bundleId, b);

            int duration = 0;
            for (int i = 1; i <= 5; i++) {
                Long videoId = (Long) row.get("video" + i);
                if (videoId == null)
                    continue;

                Video video = videos.get(videoId);
                BundleVideo bv = BundleVideo.builder()
                        .bundle(b)
                        .video(video)
                        .sequence(i)
                        .build();

                if (!(bundleVideos.containsKey(b))) {
                   bundleVideos.put(b, new BundleVideoParseVo());
                }
                bundleVideos.get(b).add(bv);
                duration += video.getDuration();
            }
            b.setDuration(duration);
        }
    }


    void parseVideo(
            List<Map<String, Object>> videoSheetInput,
            VideoParseVo videos
    ) {
        for (Map<String, Object> row : videoSheetInput) {
            Long videoId = (Long) row.get("id");
            if (videoId == null)
                continue;

            VideoKind videoKind = VideoKind.fromCode((String) row.get("kind_id"));
            TargetKind targetKind = TargetKind.fromCode((String) row.get("target_kind_id"));
            WorkoutPurposeKind purpose1 = WorkoutPurposeKind.fromCode((String) row.get("purpose1"));
            WorkoutPurposeKind purpose2 = WorkoutPurposeKind.fromCode((String) row.get("purpose2"));
            if (videoKind == null || targetKind == null)
                throw new InvalidXlsxTemplateApiException();
            if (purpose1 == null || purpose2 == null)
                throw new InvalidXlsxTemplateApiException();

            List<LookupWorkoutPurpose> firstPriorityPurposes = new ArrayList<>();
            firstPriorityPurposes.add(LookupWorkoutPurpose.fromSelection(purpose1));
            List<LookupWorkoutPurpose> secondPriorityPurposes = new ArrayList<>();
            secondPriorityPurposes.add(LookupWorkoutPurpose.fromSelection(purpose2));

            Video video = Video.builder()
                    .id(videoId)
                    .kind(LookupVideoKind.fromSelection(videoKind))
                    .targetKind(LookupTarget.fromSelection(targetKind))
                    .name((String) row.get("name"))
                    .description((String) row.get("description"))
                    .script((String) row.get("script"))
                    .duration(Math.toIntExact((Long) row.get("duration")))
                    .thumbnailPath((String) row.get("thumbnail_path"))
                    .videoPath((String) row.get("video_path"))
                    .firstPriorityPurposes(firstPriorityPurposes)
                    .secondPriorityPurposes(secondPriorityPurposes)
                    .build();

            videos.put(videoId, video);
        }
    }

}

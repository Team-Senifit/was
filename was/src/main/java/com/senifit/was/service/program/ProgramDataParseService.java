package com.senifit.was.service.program;

import com.senifit.was.entity.*;
import com.senifit.was.entity.lookup.*;
import com.senifit.was.entity.selections.*;
import com.senifit.was.repository.program.ProgramRepository;
import com.senifit.was.service.ParseXlsxService;
import com.senifit.was.service.program.exception.InvalidXlsxTemplateApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProgramDataParseService {
    private final ParseXlsxService parseXlsxService;

    public void parseProgramAndProgramBundle(
            List<Map<String, Object>> programSheetInput,
            Map<Long, Bundle> bundleMapInput,
            Map<Program, ProgramBundle> programBundleMapOutput,
            Map<Long, Program> programMapOutput
    ) {
        for (Map<String, Object> row : programSheetInput) {
            Long programId = (Long) row.get("id");
            if (programId == null)
                continue;

            LookupWorkoutCognitiveKind cognitiveKind =
                    LookupWorkoutCognitiveKind.fromSelection(
                            CognitiveKind.fromCode((String) row.get("cognitive_workout_kind_id")));
            LookupWorkoutSingingKind singingKind =
                    LookupWorkoutSingingKind.fromSelection(
                            IncludesSinging.fromCode((String) row.get("singing_workout_kind_id")));
            LookupTarget targetKind =
                    LookupTarget.fromSelection(
                            TargetKind.fromCode((String) row.get("primary_target_kind_id")));

            Program p = Program.builder()
                    .id(programId)
                    .name((String) row.get("name"))
                    .description((String) row.get("description"))
                    .duration(Math.toIntExact((Long) row.get("duration")))
                    .cognitiveWorkoutKind(cognitiveKind)
                    .singingWorkoutKind(singingKind)
                    .primaryTarget(targetKind).build();
            programMapOutput.put(programId, p);

            for (int i = 1; i <= 15; i++) {
                Long bundleId = (Long) row.get("bundle" + i);
                if (bundleId == null)
                    continue;
                if (!bundleMapInput.containsKey(bundleId))
                    throw new InvalidXlsxTemplateApiException();

                ProgramBundle pb = ProgramBundle.builder()
                        .program(p)
                        .bundle(bundleMapInput.get(bundleId))
                        .sequence(i)
                        .build();
                programBundleMapOutput.put(p, pb);
            }

        }
    }

    public void parseBundleAndBundleVideo(
        List<Map<String, Object>> bundleSheetInput,
        Map<Long, Video> videoMapInput,
        Map<Bundle, BundleVideo> bundleVideoMapOutput,
        Map<Long, Bundle> bundleMapOutput
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
            bundleMapOutput.put(bundleId, b);

            int duration = 0;
            for (int i = 1; i <= 5; i++) {
                Long videoId = (Long) row.get("video" + i);
                if (videoId == null)
                    continue;

                Video video = videoMapInput.get(videoId);
                BundleVideo bv = BundleVideo.builder()
                        .bundle(b)
                        .video(video)
                        .sequence(i)
                        .build();

                bundleVideoMapOutput.put(b, bv);
                duration += video.getDuration();
            }
            b.setDuration(duration);
        }
    }


    public void parseVideo(
            List<Map<String, Object>> videoSheetInput,
            Map<Long, Video> videoMapOutput
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

            List<LookupWorkoutPurpose> purposes = new ArrayList<>();
            purposes.add(LookupWorkoutPurpose.fromSelection(purpose1));
            purposes.add(LookupWorkoutPurpose.fromSelection(purpose2));

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
                    .purposes(purposes)
                    .build();

            videoMapOutput.put(videoId, video);
        }
    }

    public void validate(
        Map<Long, Video> videos,
        Map<Bundle, BundleVideo> bundleVideos,
        Map<Long, Bundle> bundles,
        Map<Program, ProgramBundle> programBundles,
        Map<Long, Program> programs
    ) {
        for (Bundle bundle : bundles.values()) {
            bundle
        }
    }

}

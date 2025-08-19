package com.senifit.was.service.workoutData;

import com.senifit.was.entity.*;
import com.senifit.was.repository.bundle.BundleRepository;
import com.senifit.was.repository.bundle.BundleVideoRepository;
import com.senifit.was.repository.bundle.ProgramBundleRepository;
import com.senifit.was.repository.program.ProgramRepository;
import com.senifit.was.repository.program.ProgramStatRepository;
import com.senifit.was.repository.video.VideoRepository;
import com.senifit.was.service.ParseXlsxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkoutDataService {
    private final ProgramRepository programRepository;
    private final ProgramStatRepository programStatRepository;
    private final BundleRepository bundleRepository;
    private final VideoRepository videoRepository;
    private final ProgramBundleRepository programBundleRepository;
    private final BundleVideoRepository bundleVideoRepository;
    private final WorkoutDataParseService workoutDataParseService;
    private final ParseXlsxService parseXlsxService;

    @Transactional
    protected <T, ID> void insertData(
            JpaRepository<T, ID> repository, Collection<T> entities, String name
    ) {
        log.info("Inserting {} {}", entities.size(), name);
        repository.saveAll(entities);
        log.info("Done Inserting {} {}", entities.size(), name);
    }

    @Transactional
    public void createPrograms(Collection<Program> programs) {
        insertData(programRepository, programs, "programs");
    }
    @Transactional
    public void createBundles(Collection<Bundle> bundles) {
        insertData(bundleRepository, bundles, "bundles");
    }
    @Transactional
    public void createVideos(Collection<Video> videos) {
        insertData(videoRepository, videos, "videos");
    }
    @Transactional
    public void createProgramBundles(Collection<ProgramBundle> programBundles) {
        insertData(programBundleRepository, programBundles, "programBundles");
    }
    @Transactional
    public void createBundleVideos(Collection<BundleVideo> bundleVideos) {
        insertData(bundleVideoRepository, bundleVideos, "bundleVideos");
    }
    @Transactional
    public void createWorkoutData(
            Collection<Program> programs,
            Collection<Bundle> bundles,
            Collection<Video> videos,
            Collection<ProgramBundle> programBundles,
            Collection<BundleVideo> bundleVideos) {
        // !!ORDER MATTERS!!
        createVideos(videos);
        createBundles(bundles);
        createPrograms(programs);
        createBundleVideos(bundleVideos);
        createProgramBundles(programBundles);
    }

    @Transactional
    public void loadFromXlsx() throws IOException {
       createFromXlsxInputStream(new ClassPathResource("program-data-template.xlsx").getInputStream());
    }

    @Transactional
    void createFromXlsxInputStream(InputStream xlsxInputStream) throws IOException {
        try (xlsxInputStream) {
            List<List<Map<String, Object>>> parsed = parseXlsxService.parse(xlsxInputStream);
            List<Map<String, Object>> videoSheet = parsed.get(0);
            List<Map<String, Object>> bundleSheet = parsed.get(1);
            List<Map<String, Object>> programSheet = parsed.get(2);

            VideoParseVo videoParseVo = new VideoParseVo();
            BundleVideoListParseVo bundleVideoListParseVo = new BundleVideoListParseVo();
            BundleParseVo bundleParseVo = new BundleParseVo();
            ProgramBundleListParseVo programBundleListParseVo = new ProgramBundleListParseVo();
            ProgramParseVo programParseVo = new ProgramParseVo();

            workoutDataParseService.parseVideo(
                    videoSheet, videoParseVo
            );
            workoutDataParseService.parseBundleAndBundleVideo(
                    bundleSheet, videoParseVo, bundleVideoListParseVo, bundleParseVo
            );
            workoutDataParseService.parseProgramAndProgramBundle(
                    programSheet, bundleParseVo, programBundleListParseVo, programParseVo
            );

            createWorkoutData(
                    programParseVo.toCollection(),
                    bundleParseVo.toCollection(),
                    videoParseVo.toCollection(),
                    programBundleListParseVo.toCollection(),
                    bundleVideoListParseVo.toCollection()
            );
        }
    }


}

package com.senifit.was.bootstrap.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VideoDataCacheInitializerRunner implements ApplicationRunner {
    private final VideoDataCacheInitializer videoDataCacheInitializer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Initializing VideoCacheService");
        videoDataCacheInitializer.initialize();
        log.info("Finished initializing VideoCacheService");
    }
}

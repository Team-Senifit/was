package com.senifit.was;

import com.senifit.was.entity.*;
import com.senifit.was.entity.lookup.*;
import com.senifit.was.entity.selections.*;
import com.senifit.was.service.ParseXlsxService;
import com.senifit.was.service.program.ProgramDataParseService;
import com.senifit.was.service.program.exception.InvalidXlsxTemplateApiException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@SpringBootTest
class WasApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testFromXlsx() throws IOException {
		ClassPathResource resource = new ClassPathResource("program-data-template.xlsx");
		try (InputStream is = resource.getInputStream()) {
			ParseXlsxService parseXlsxService = new ParseXlsxService();
			ProgramDataParseService programDataParseService = new ProgramDataParseService(parseXlsxService);
			List<List<Map<String, Object>>> parsed =  parseXlsxService.parse(is);

			List<Map<String, Object>> videoSheet = parsed.get(0);
			List<Map<String, Object>> bundleSheet = parsed.get(1);
			List<Map<String, Object>> programSheet = parsed.get(2);

			Map<Long, Video> videos = new HashMap<>();
			Map<Bundle, BundleVideo> bundleVideos = new HashMap<>();
			Map<Long, Bundle> bundles = new HashMap<>();
			Map<Program, ProgramBundle> programBundles = new HashMap<>();
			Map<Long, Program> programs = new HashMap<>();

			programDataParseService.parseVideo(videoSheet, videos);
			programDataParseService.parseBundleAndBundleVideo(bundleSheet, videos, bundleVideos, bundles);
			programDataParseService.parseProgramAndProgramBundle(programSheet, bundles, programBundles, programs);


		}
	}

}

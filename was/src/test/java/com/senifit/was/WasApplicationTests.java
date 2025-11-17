package com.senifit.was;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class WasApplicationTests {

	@Autowired
	public WorkoutDataService workoutDataService;


	@Test
	void contextLoads() {
	}

	@Test
	void xlsx() throws IOException {
		workoutDataService.loadFromXlsx();
	}
}


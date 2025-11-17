package com.senifit.was.repository.program.api;

import com.senifit.was.entity.Program;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalProgramRepository {
    Program getPersonalByHash(String hash);
}

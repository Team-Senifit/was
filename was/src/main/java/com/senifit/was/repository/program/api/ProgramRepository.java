package com.senifit.was.repository.program.api;

import com.senifit.was.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long>, PersonalProgramRepository {
    Program getProgramByHash(String hash);
}

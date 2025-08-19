package com.senifit.was.repository.program;

import com.senifit.was.entity.Program;
import com.senifit.was.entity.ProgramStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramStatRepository extends JpaRepository<ProgramStat, Long> {
}

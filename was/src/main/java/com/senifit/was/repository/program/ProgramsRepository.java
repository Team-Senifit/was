package com.senifit.was.repository.program;

import com.senifit.was.entity.Programs;
import com.senifit.was.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramsRepository extends JpaRepository<Programs, Long> {
}

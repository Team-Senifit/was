package com.senifit.was.repository.instructor;


import com.senifit.was.entity.Instructors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorsRepository extends JpaRepository<Instructors, Long> {
}

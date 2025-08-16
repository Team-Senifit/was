package com.senifit.was.repository.lookup;

import com.senifit.was.entity.lookup.LookupGender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookupGenderRepository extends JpaRepository<LookupGender, Long> {}


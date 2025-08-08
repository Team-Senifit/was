package com.senifit.was.repository.lookup;

import com.senifit.was.entity.LookupContentKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookupContentKindRepository extends JpaRepository<LookupContentKind, Long> {}


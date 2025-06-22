package com.senifit.was.repository.center;

import com.senifit.was.entity.Centers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentersRepository extends JpaRepository<Centers, Long> {
}

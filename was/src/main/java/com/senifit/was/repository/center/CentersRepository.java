package com.senifit.was.repository.center;

import com.senifit.was.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CentersRepository extends JpaRepository<Center, Long> {
    Optional<Center> findByCenterCode(Long centerCode);
    boolean existsByCenterCode(Long centerCode);
}

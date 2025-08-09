package com.senifit.was.repository.center;

import com.senifit.was.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CentersRepository extends JpaRepository<Center, Long> {
    Optional<Center> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}

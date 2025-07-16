package com.senifit.was.repository.center;

import com.senifit.was.entity.Centers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CentersRepository extends JpaRepository<Centers, Long> {
    boolean existsByid(String id); // 아이디 중복확인에 사용
    Optional<Centers> findByid(String id);
}

package com.senifit.was.repository.center;

import com.senifit.was.entity.Centers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentersRepository extends JpaRepository<Centers, Long> {
    boolean existsByid(String id); // 아이디 중복확인에 사용
}

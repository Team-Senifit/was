package com.senifit.was.repository.member;

import com.senifit.was.entity.Members;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembersRepositoryCustom {

    List<Members> findTop5ByCenterIdOrderByCreatedAtDesc(Long centerId);
}

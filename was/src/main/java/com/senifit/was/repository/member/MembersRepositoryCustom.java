package com.senifit.was.repository.member;

import com.senifit.was.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembersRepositoryCustom {

    List<Member> findTop5ByCenterIdOrderByCreatedAtDesc(Long centerId);
}

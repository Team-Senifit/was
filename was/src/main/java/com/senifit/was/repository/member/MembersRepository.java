package com.senifit.was.repository.member;

import com.senifit.was.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<Members,Long> ,MembersRepositoryCustom{
}

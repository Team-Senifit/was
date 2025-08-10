package com.senifit.was.repository.record;

import com.senifit.was.entity.MemberRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordsMembersRepository extends JpaRepository<MemberRecord, Long> {
    Optional<MemberRecord> findByRecord_RecordIdAndMember_Center_CenterId(Long recordId, Long centerId);
}

package com.senifit.was.repository.record;

import com.senifit.was.entity.RecordsMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordsMembersRepository extends JpaRepository<RecordsMembers, Long> {
    Optional<RecordsMembers> findByRecords_RecordIdAndMembers_Centers_CenterId(Long recordId, Long centerId);
}

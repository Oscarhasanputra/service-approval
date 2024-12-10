package com.bit.microservices.service_approval.repository;

import com.bit.microservices.service_approval.entity.MsEventApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface MsEventApprovalRepository extends JpaRepository<MsEventApproval, String>, QuerydslPredicateExecutor<MsEventApproval> {
    List<MsEventApproval> findByCodeIn(Collection<String> codes);
    List<MsEventApproval> findByIdIn(Collection<String> ids);
}

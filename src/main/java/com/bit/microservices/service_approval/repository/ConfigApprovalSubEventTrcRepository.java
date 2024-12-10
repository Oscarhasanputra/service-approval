package com.bit.microservices.service_approval.repository;

import com.bit.microservices.service_approval.entity.ConfigApprovalSubEventTrc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConfigApprovalSubEventTrcRepository extends JpaRepository<ConfigApprovalSubEventTrc, String>, QuerydslPredicateExecutor<ConfigApprovalSubEventTrc> {
    List<ConfigApprovalSubEventTrc> findByTrxConfigapprovalIdIn(Collection<String> trxConfigapprovalIds);
    List<ConfigApprovalSubEventTrc> findByIdIn(Collection<String> ids);
}

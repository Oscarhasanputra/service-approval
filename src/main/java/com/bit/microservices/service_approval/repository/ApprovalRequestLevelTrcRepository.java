package com.bit.microservices.service_approval.repository;

import com.bit.microservices.service_approval.entity.ApprovalRequestLevelTrc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ApprovalRequestLevelTrcRepository extends JpaRepository<ApprovalRequestLevelTrc, String>, QuerydslPredicateExecutor<ApprovalRequestLevelTrc> {
    List<ApprovalRequestLevelTrc> findByIdIn(Collection<String> ids);
}

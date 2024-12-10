package com.bit.microservices.service_approval.repository;

import com.bit.microservices.service_approval.entity.ApprovalRequestLevelAssigneeTrc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ApprovalRequestLevelAssigneeTrcRepository extends JpaRepository<ApprovalRequestLevelAssigneeTrc, String>, QuerydslPredicateExecutor<ApprovalRequestLevelAssigneeTrc> {
    List<ApprovalRequestLevelAssigneeTrc> findByIdIn(Collection<String> ids);
}

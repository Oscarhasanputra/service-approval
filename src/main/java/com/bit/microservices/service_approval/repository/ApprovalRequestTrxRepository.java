package com.bit.microservices.service_approval.repository;

import com.bit.microservices.service_approval.entity.ApprovalRequestTrx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ApprovalRequestTrxRepository extends JpaRepository<ApprovalRequestTrx, String>, QuerydslPredicateExecutor<ApprovalRequestTrx> {
    List<ApprovalRequestTrx> findByIdIn(Collection<String> ids);
}

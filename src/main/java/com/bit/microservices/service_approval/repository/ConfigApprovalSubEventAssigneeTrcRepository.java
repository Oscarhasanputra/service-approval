package com.bit.microservices.service_approval.repository;

import com.bit.microservices.service_approval.entity.ConfigApprovalSubEventAssigneeTrc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConfigApprovalSubEventAssigneeTrcRepository extends JpaRepository<ConfigApprovalSubEventAssigneeTrc, String>, QuerydslPredicateExecutor<ConfigApprovalSubEventAssigneeTrc> {
    List<ConfigApprovalSubEventAssigneeTrc> findByIdIn(Collection<String> ids);

    List<ConfigApprovalSubEventAssigneeTrc> findByTrcConfigapprovalsubeventIdIn(Collection<String> trcConfigapprovalsubeventIds);
}

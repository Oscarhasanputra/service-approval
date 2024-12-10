package com.bit.microservices.service_approval.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "trx_configapproval")
@EntityListeners({AuditingEntityListener.class})
public class ConfigApprovalTrx extends AuditedField implements Serializable {

    @Serial
    private static final long serialVersionUID = -71811109708045672L;
    @Id
//    @GeneratedValue
    @JdbcTypeCode(Types.VARCHAR)
    private String id;


    @Column(nullable = false,columnDefinition = "TIMESTAMP")
    private LocalDateTime effectiveDate;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String msBranchId;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String msBranchCode;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String msEventapprovalId;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String msEventapprovalCode;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String frontendView;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String endpointOnFinished;

    @Column(nullable = true,columnDefinition = "varchar(255)")
    private String remarks;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(nullable = true,columnDefinition = "varchar(255)")
    private String deletedReason;


}

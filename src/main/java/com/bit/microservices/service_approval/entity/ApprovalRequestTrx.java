package com.bit.microservices.service_approval.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Types;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "trx_approvalrequest")
@EntityListeners({AuditingEntityListener.class})
public class ApprovalRequestTrx extends AuditedField implements Serializable {

    @Serial
    private static final long serialVersionUID = 3662820945026601574L;

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private String id;

    @Column(nullable = false,columnDefinition = "timestamp")
    private LocalDateTime date;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String trxConfigapprovalId;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String code;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String msBranchId;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String msBranchCode;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String status;

    @Column(nullable = false)
    private Boolean stopWhenRejected;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String frontendView;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String endpointOnFinished;

    @Column(nullable = true,columnDefinition = "timestamp")
    private LocalDateTime expiredDate;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String remarks;

    @Column(nullable = false)
    private Boolean isDeleted=false;

    @Column(nullable = true,columnDefinition = "varchar(255)")
    private String deletedReason;


}

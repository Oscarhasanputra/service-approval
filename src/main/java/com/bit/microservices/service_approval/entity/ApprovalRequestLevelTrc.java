package com.bit.microservices.service_approval.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Types;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "trc_approvalrequestlevel")
public class ApprovalRequestLevelTrc implements Serializable {

    @Serial
    private static final long serialVersionUID = -122826611178347386L;
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private String id;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String trxApprovalrequestId;

    @Column(nullable = false,columnDefinition = "integer")
    private Integer level;

    @Column(nullable = false,columnDefinition = "integer")
    private Integer totalApprovalNeeded;

    @Column(nullable = true,columnDefinition = "timestamp")
    private LocalDateTime sendNotificationDate;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String approvalStatus;

    @Column(nullable = true,columnDefinition = "timestamp")
    private LocalDateTime approvalDate;

    @Column(nullable = true,columnDefinition = "varchar(255)")
    private String approvalNote;

}

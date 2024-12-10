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
@Table(name = "trc_approvalrequestlevelassignee")
public class ApprovalRequestLevelAssigneeTrc implements Serializable {

    @Serial
    private static final long serialVersionUID = 2810339546022344804L;

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private String id;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String trcApprovalrequestlevelId;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String assigneeId;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String assigneeName;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String assigneeEmail;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String assigneeWhatsapp;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String assigneeTelegram;

    @Column(nullable = false)
    private Boolean sendEmailNotification;

    @Column(nullable = false)
    private Boolean sendWaNotification;

    @Column(nullable = false)
    private Boolean sendTelegramNotification;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String approvalStatus;

    @Column(nullable = true,columnDefinition = "timestamp")
    private LocalDateTime approvalDate;

    @Column(nullable = true,columnDefinition = "varchar(255)")
    private String approvalNote;
}

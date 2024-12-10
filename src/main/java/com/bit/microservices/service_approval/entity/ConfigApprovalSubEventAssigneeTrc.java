package com.bit.microservices.service_approval.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Types;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "trc_configapprovalsubeventassignee")
public class ConfigApprovalSubEventAssigneeTrc implements Serializable {

    @Serial
    private static final long serialVersionUID = 3693754733649544303L;

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private String id;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String trcConfigapprovalsubeventId;

    @Column(nullable = false,columnDefinition = "integer")
    private Integer level;

    @Column(nullable = false,columnDefinition = "integer")
    private Integer totalApprovalNeeded;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String assigneeType;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String assigneeId;

    @Column(nullable = true,columnDefinition = "varchar(255)")
    private String assigneeCode;


}

package com.bit.microservices.service_approval.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "ms_eventapproval")
@EntityListeners({AuditingEntityListener.class})
public class MsEventApproval extends AuditedField implements Serializable {
    @Serial
    private static final long serialVersionUID = 5557171717066378327L;

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private String id;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String code;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String name;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String applicationCode;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = true,columnDefinition = "varchar(255)")
    private String remarks;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(nullable = true,columnDefinition = "varchar(255)")
    private String deletedReason;

}

package com.bit.microservices.service_approval.entity;

import com.bit.microservices.service_approval.enums.ConfigApprovalSubEventEnum;
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
@Table(name = "trc_configapprovalsubevent")
public class ConfigApprovalSubEventTrc implements Serializable {
    @Serial
    private static final long serialVersionUID = -1849455748501826654L;

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private String id;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String trxConfigapprovalId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,columnDefinition = "varchar(255)")
    private ConfigApprovalSubEventEnum type;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String code;

    @Column(nullable = false)
    private Boolean autoApproved = false;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String remarks;

    @Column(nullable = false)
    private Boolean autoSendRequest = true;

    @Column(nullable = false)
    private Boolean skipWhenRejected = true;

}

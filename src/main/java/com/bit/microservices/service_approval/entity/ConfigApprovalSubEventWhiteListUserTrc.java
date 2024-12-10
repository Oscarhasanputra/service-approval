package com.bit.microservices.service_approval.entity;

import com.bit.microservices.service_approval.enums.WhiteListUserEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Types;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "trc_configapprovalsubeventwhitelistuser")
public class ConfigApprovalSubEventWhiteListUserTrc implements Serializable {

    @Serial
    private static final long serialVersionUID = 4334525840415560106L;

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private String id;


    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String trcConfigapprovalsubeventId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,columnDefinition = "varchar(255)")
    private WhiteListUserEnum whitelistuserType;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String whitelistuserId;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    private String whitelistuserCode;
}

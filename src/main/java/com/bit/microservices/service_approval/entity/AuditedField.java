package com.bit.microservices.service_approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@Audited
@NoArgsConstructor
@AllArgsConstructor
public class AuditedField implements Serializable {

    @Serial
    private static final long serialVersionUID = 2991741778556770933L;

    @Column(nullable = false,columnDefinition = "varchar(255)")
    @CreatedBy
    private String createdBy;

    @Column(nullable = false,columnDefinition = "TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(nullable = true,columnDefinition = "varchar(255)")
    @LastModifiedBy
    private String modifiedBy;

    @Column(nullable = true,columnDefinition = "TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
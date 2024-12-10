package com.bit.microservices.service_approval.model;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class ConfigApprovalGraphqlDto implements Serializable {

    private String id;

    private String branch;

    private OffsetDateTime effectiveDate;

    private String msEventapprovalId;

    private String msEventapprovalCode;

    private Boolean autoSendRequest;

    private Boolean stopWhenRejected;

    private String frontendView;

    private String endpointOnFinished;

    private String remarks;

    private Boolean isDeleted = false;

    private String deletedReason;

    private String createdBy;

    private OffsetDateTime createdDate;

    private String modifiedBy;

    private OffsetDateTime modifiedDate;
}

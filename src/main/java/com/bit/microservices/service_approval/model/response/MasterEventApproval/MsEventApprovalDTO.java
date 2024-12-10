package com.bit.microservices.service_approval.model.response.MasterEventApproval;

import com.bit.microservices.utils.FieldInfo;
import com.bit.microservices.utils.OffsetDateTimeDeserializer;
import com.bit.microservices.utils.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Types;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class MsEventApprovalDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5871035262247806302L;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "id",alias = "Master Event Approval ID" )
    private String id;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "code",alias = "Master Event Approval Code")
    private String code;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "name",alias = "Master Event Approval Name")
    private String name;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "application_code",alias = "Master Event Approval Application Code")
    private String applicationCode;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "is_active",alias = "Status Active Master Event Approval")
    private Boolean isActive;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "remarks",alias = "Master Event Approval Remarks")
    private String remarks;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "is_deleted",alias = "Status Deleted Master Event Approval")
    private Boolean deleted;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "deleted_reason",alias = "Delete Reason of Master Event Approval")
    private String deletedReason;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "created_by",alias = "Person who Created Config Approval")
    private String createdBy;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "created_date",alias = "Date of Creation Config Approval")
    @JsonSerialize(using= OffsetDateTimeSerializer.class)
    @JsonDeserialize(using= OffsetDateTimeDeserializer.class)
    private OffsetDateTime createdDate;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "modified_by",alias = "Person who Modified Config Approval")
    private String modifiedBy;

    @FieldInfo(entityName = "ms_eventapproval",fieldName = "modified_date",alias = "Date Of Modification Config Approval")
    @JsonSerialize(using= OffsetDateTimeSerializer.class)
    @JsonDeserialize(using= OffsetDateTimeDeserializer.class)
    private OffsetDateTime modifiedDate;
}

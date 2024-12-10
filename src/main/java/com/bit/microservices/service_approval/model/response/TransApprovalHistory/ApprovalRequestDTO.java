package com.bit.microservices.service_approval.model.response.TransApprovalHistory;

import com.bit.microservices.service_approval.model.response.TransConfigApproval.ConfigApprovalSubeventDTO;
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
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ApprovalRequestDTO implements Serializable {


    @Serial
    private static final long serialVersionUID = -6849691561843846928L;
    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "id",alias = "Approval Request Id" )
    private String id;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "date",alias = "Approval Request Date" )
    @JsonSerialize(using= OffsetDateTimeSerializer.class)
    @JsonDeserialize(using= OffsetDateTimeDeserializer.class)
    private OffsetDateTime data;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "trx_configapproval_id",alias = "Config Approval Id" )
    private String configapprovalId;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "code",alias = "Approval Request Code" )
    private String code;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "ms_branch_id",alias = "Approval Request Branch Id" )
    private String branchId;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "ms_branch_code",alias = "Approval Request Branch Code" )
    private String branchCode;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "status",alias = "Approval Request Status" )
    private String status;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "stop_when_rejected",alias = "Approval Request " )
    private Boolean stopWhenRejected;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "frontend_view",alias = "Approval Request frontend view" )
    private String frontendView;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "endpoint_on_finished",alias = "Approval Request endpoint" )
    private String endpointOnFinished;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "expired_date",alias = "Approval Request Expired Date" )
    @JsonSerialize(using= OffsetDateTimeSerializer.class)
    @JsonDeserialize(using= OffsetDateTimeDeserializer.class)
    private OffsetDateTime expiredDate;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "remarks",alias = "Approval Request Remark/note" )
    private String remarks;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "is_deleted",alias = "Approval Request status delete" )
    private Boolean deleted;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "deleted_reason",alias = "Approval Request Deleted Reason" )
    private String deletedReason;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "created_by",alias = "Person who Created Config Approval")
    private String createdBy;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "created_date",alias = "Date of Creation Config Approval")
    @JsonSerialize(using= OffsetDateTimeSerializer.class)
    @JsonDeserialize(using= OffsetDateTimeDeserializer.class)
    private OffsetDateTime createdDate;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "modified_by",alias = "Person who Modified Config Approval")
    private String modifiedBy;

    @FieldInfo(entityName = "trx_approvalrequest",fieldName = "modified_date",alias = "Date Of Modification Config Approval")
    @JsonSerialize(using= OffsetDateTimeSerializer.class)
    @JsonDeserialize(using= OffsetDateTimeDeserializer.class)
    private OffsetDateTime modifiedDate;

}

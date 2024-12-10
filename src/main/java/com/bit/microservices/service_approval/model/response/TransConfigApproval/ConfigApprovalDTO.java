package com.bit.microservices.service_approval.model.response.TransConfigApproval;

import com.bit.microservices.utils.FieldInfo;
import com.bit.microservices.utils.OffsetDateTimeDeserializer;
import com.bit.microservices.utils.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
//@RelatedEntities(value={
//        @RelatedEntity(prefix = "subevent",d= ConfigApprovalSubeventDTO.class)
//})
public class ConfigApprovalDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3730824910863369257L;



    @FieldInfo(entityName = "trx_configapproval",fieldName = "id",alias = "Config Approval Id" )
    private String id;
    @FieldInfo(entityName = "trx_configapproval",fieldName = "ms_branch_id",alias = "Config Approval Branch" )
    private String branchId;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "ms_branch_code",alias = "Config Approval Branch")
    private String branchCode;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "effective_date",alias = "Config Approval Effective Date")
    @JsonSerialize(using= OffsetDateTimeSerializer.class)
    @JsonDeserialize(using= OffsetDateTimeDeserializer.class)
    private OffsetDateTime effectiveDate;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "ms_eventapproval_id",alias = "Config Approval Master Event Approval ID")
    private String eventapprovalId;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "ms_eventapproval_code",alias = "Config Approval Master Event Approval Code")
    private String eventapprovalCode;


    @FieldInfo(entityName = "trx_configapproval",fieldName = "frontend_view",alias = "Config Approval Front End URL View")
    private String frontendView;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "endpoint_on_finished",alias = "Config Approval URL when finished")
    private String endpointOnFinished;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "remarks",alias = "Config Approval Remarks Note")
    private String remarks;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "is_deleted",alias = "Status Deleted Config Approval")
    private Boolean deleted;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "deleted_reason",alias = "Reason Delete Of Config Approval")
    private String deletedReason;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "created_by",alias = "Person who Created Config Approval")
    private String createdBy;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "created_date",alias = "Date of Creation Config Approval")
    @JsonSerialize(using= OffsetDateTimeSerializer.class)
    @JsonDeserialize(using= OffsetDateTimeDeserializer.class)
    private OffsetDateTime createdDate;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "modified_by",alias = "Person who Modified Config Approval")
    private String modifiedBy;

    @FieldInfo(entityName = "trx_configapproval",fieldName = "modified_date",alias = "Date Of Modification Config Approval")
    @JsonSerialize(using= OffsetDateTimeSerializer.class)
    @JsonDeserialize(using= OffsetDateTimeDeserializer.class)
    private OffsetDateTime modifiedDate;

    private List<ConfigApprovalSubeventDTO> subevent;
}

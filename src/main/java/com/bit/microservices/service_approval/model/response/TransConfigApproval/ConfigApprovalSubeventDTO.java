package com.bit.microservices.service_approval.model.response.TransConfigApproval;

import com.bit.microservices.service_approval.entity.ConfigApprovalSubEventAssigneeTrc;
import com.bit.microservices.service_approval.enums.ConfigApprovalSubEventEnum;
import com.bit.microservices.utils.FieldInfo;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Types;
import java.util.List;

@Data
@NoArgsConstructor
public class ConfigApprovalSubeventDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3774848283846793748L;

    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "id",alias = "Config Approval Subevent ID" , havingFilter = true)
    private String id;

    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "trx_configapproval_id",alias = "Config Approval ID of Subevent" , havingFilter = true)
    private String trxConfigapprovalId;

    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "type",alias = "Config Approval Subevent Type" , havingFilter = true)
    private String type;

    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "code",alias = "Config Approval Subevent Code" , havingFilter = true)
    private String code;

    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "id",alias = "Config Approval Subevent remarks" , havingFilter = true)
    private String remarks;

    private List<ConfigApprovalSubeventAssigneeDTO> assignee;
}

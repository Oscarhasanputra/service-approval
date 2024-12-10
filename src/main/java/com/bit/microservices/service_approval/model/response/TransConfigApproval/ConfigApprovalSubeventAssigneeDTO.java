package com.bit.microservices.service_approval.model.response.TransConfigApproval;

import com.bit.microservices.utils.FieldInfo;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.sql.Types;

@Data
@NoArgsConstructor
public class ConfigApprovalSubeventAssigneeDTO implements Serializable {
    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "id",alias = "Config Approval Subevent ID" , havingFilter = true)
    private String id;

    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "id",alias = "Config Approval Subevent ID" , havingFilter = true)
    private String trcConfigapprovalsubeventId;

    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "id",alias = "Config Approval Subevent ID" , havingFilter = true)
    private Integer level;

    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "id",alias = "Config Approval Subevent ID" , havingFilter = true)
    private Integer totalApprovalNeeded;

    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "id",alias = "Config Approval Subevent ID" , havingFilter = true)
    private String assigneeType;

    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "id",alias = "Config Approval Subevent ID" , havingFilter = true)
    private String assigneeId;

    @FieldInfo(entityName = "trc_configapprovalsubevent",fieldName = "id",alias = "Config Approval Subevent ID" , havingFilter = true)
    private String assigneeAttribute;

}

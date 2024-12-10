package com.bit.microservices.service_approval.model.request.TransConfigApproval;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class SubEventTransConfigApprovalDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3702550723993549460L;
    @Schema(description = "ID of Sub event Config Approval",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String id;

    @Schema(description = "Type of Event",
            example = "SUBEVENT",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String type;

    @Schema(description = "Code Subevent",
            example = "SMALL",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String code;

    @Schema(description = "Remarks of Event",
            example = "false",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean autoApproved;

    @Schema(description = "Set if creation Event Approval always want to send Request Approval",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    private Boolean autoSendRequest;

    @Schema(description = "Set if creation Event Approval whether stop when rejected or not",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    private Boolean stopWhenRejected;

    @Schema(description = "Remarks of Event",
            example = "",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String remarks;

    @Schema(description = "Event Approval Assignee")
    @Valid
    @NotNull
    private List<WhiteListUserConfigApprovalDTO> whitelistuser;

    @Schema(description = "Event Approval Assignee")
    @Valid
    @NotNull
    private List<AssigneeSubEventTransConfigDTO> assignee;
}

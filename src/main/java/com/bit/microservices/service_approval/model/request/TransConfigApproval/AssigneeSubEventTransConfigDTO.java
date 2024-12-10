package com.bit.microservices.service_approval.model.request.TransConfigApproval;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class AssigneeSubEventTransConfigDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 243439700201525569L;
    @Schema(description = "Id of Sub Event Config ",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String id;

    @Schema(description = "Source Program Event Approval",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String level;

    @Schema(description = "Source Program Event Approval",
            example = "3",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String totalApprovalNeeded;

    @Schema(description = "Source Program Event Approval",
            example = "USER",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String assigneeType;

    @Schema(description = "Source Program Event Approval",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String assigneeId;

    @Schema(description = "Source Program Event Approval",
            example = "ATTRIBUTE",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String assigneeCode;





}

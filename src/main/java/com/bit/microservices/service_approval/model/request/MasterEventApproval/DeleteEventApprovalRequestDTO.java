package com.bit.microservices.service_approval.model.request.MasterEventApproval;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteEventApprovalRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3579887003786378352L;

    @Schema(description = "ID of Event Approval",
            example = "MYCOMPANY-CL~001",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String id;

    @Schema(description = "Reason of Deletion Event Approval",
            example = "EVENT TIDAK JADI DIBUAT",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String deletedReason;

}

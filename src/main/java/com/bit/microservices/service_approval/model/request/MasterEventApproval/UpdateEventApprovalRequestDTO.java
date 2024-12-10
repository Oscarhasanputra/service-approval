package com.bit.microservices.service_approval.model.request.MasterEventApproval;

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
public class UpdateEventApprovalRequestDTO extends CreateEventApprovalRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2165077691312149908L;

    @Schema(description = "Id Master Event Approval",
            example = "MYCOMPANY",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String id;
}


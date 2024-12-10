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
public class CreateEventApprovalRequestDTO implements Serializable {


    @Serial
    private static final long serialVersionUID = -7215293757559411038L;

    @Schema(description = "Application Code",
            example = "MC",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String applicationCode;

    @Schema(description = "Code for Event Approval",
            example = "MYCOMPANY-CL",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String code;

    @Schema(description = "Name of Event Approval",
            example = "REQUEST NAIK LIMIT MITRA",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String name;

    @Schema(description = "Status of Event Approval",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean active;


    @Schema(description = "Marking Event Approval",
            example = "",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String remarks;


}

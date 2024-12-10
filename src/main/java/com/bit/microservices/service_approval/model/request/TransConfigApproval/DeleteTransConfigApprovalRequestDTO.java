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
public class DeleteTransConfigApprovalRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2608481071755922863L;


    @Schema(description = "ID of Event Approval",
            example = "COF/AUTONUMBER",
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
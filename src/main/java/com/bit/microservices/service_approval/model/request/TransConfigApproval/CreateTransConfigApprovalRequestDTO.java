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
import java.time.OffsetDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class CreateTransConfigApprovalRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8849701324811096389L;
    @Schema(description = "Trans Config Approval ID",
            example = "COF/AUTONUMBER",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String id;

    @Schema(description = "Trans Config Approval Date Applied",
            example = "now()",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    private OffsetDateTime effectiveDate;

    @Schema(description = "Event Approval ID of Trans Config Approval",
            example = "MYCOMPANY-CL~uuid",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String eventApprovalId;

    @Schema(description = "Event Approval Code of Trans Config Approval",
            example = "MYCOMPANY-CL",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String eventApprovalCode;

    @Schema(description = "Branch of Trans Config Approval",
            example = "JKT",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String branchId;

    @Schema(description = "Branch of Trans Config Approval",
            example = "JKT",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String branchCode;


    @Schema(description = "Url Endpoint View",
            example = "hutang-piutang/creditlimit/view/mitra?",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String frontendView;

    @Schema(description = "Url Endpoint when approval is finished",
            example = "hutang-piutang/creditlimit/get",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String endpointOnFinished;


    @Schema(description = "Trans Config Approval ID",
            example = "",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String remarks;


    @Schema(description = "Subevent onf Config Approval",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    @NotNull
    private List<SubEventTransConfigApprovalDTO> subevent;
}


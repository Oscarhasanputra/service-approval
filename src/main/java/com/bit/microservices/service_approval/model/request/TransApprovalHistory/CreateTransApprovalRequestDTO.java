package com.bit.microservices.service_approval.model.request.TransApprovalHistory;

import com.bit.microservices.service_approval.enums.ApprovalHistoryStatusEnum;
import com.bit.microservices.service_approval.utils.annotations.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class CreateTransApprovalRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3648787415325914169L;
    @Schema(description = "ID Approval History",
            example = "REQ/AUTONUMBER",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String id;


    @Schema(description = "Source Program Event Approval",
            example = "id",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    private OffsetDateTime date;


    @Schema(description = "Expired date of Approval History",
            example = "now()",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    private OffsetDateTime expiredDate;

    @Schema(description = "Config Approval ID",
            example = "COF/AUTONUMBER",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String configApprovalId;


    @Schema(description = "Code Approval History",
            example = "CL/JKT/2410/000001",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String code;


    @Schema(description = "Remark Approval History",
            example = "",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String remarks;

    @Schema(description = "Branch Approval",
            example = "JKT",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String branchId;

    @Schema(description = "Branch Approval",
            example = "JKT",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String branchCode;

    @Schema(description = "Status to Stop Approval",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    private Boolean stopWhenRejected;

    @Schema(description = "Front End View URL",
            example = "hutang-piutang/creditlimit/view/mitra?",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String frontendView;

    @Schema(description = "Endpoint when finished in approval",
            example = "hutang-piutang/creditlimit/get",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String endpointOnFinished;

    @Schema(description = "Current Status of Approval History",
            example = "WAITING FOR APPROVAL",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    @ValidEnum(enumClass = ApprovalHistoryStatusEnum.class)
    private String status;



    @Schema(description = "level Approval",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<LevelTransApprovalHistoryDTO> level;
}

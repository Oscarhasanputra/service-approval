package com.bit.microservices.service_approval.model.request.TransApprovalHistory;

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
public class LevelTransApprovalHistoryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7767395875469053390L;
    @Schema(description = "ID of Approval History Level",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String id;


    @Schema(description = "Level Approval",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String level;

    @Schema(description = "Total Approval to fill",
            example = "3",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String totalApprovalNeeded;

    @Schema(description = "Date when Send Notification",
            example = "now()",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    private OffsetDateTime sendNotificationDate;


    @Schema(description = "Status Approval",
            example = "WAITING FOR APPROVAL",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String approvalStatus;

    @Schema(description = "Date of Approval",
            example = "now()",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    private OffsetDateTime approvalDate;

    @Schema(description = "Note of Approval",
            example = "",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String approvalNote;


    @Schema(description = "Assignee Approval",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AssigneeTransApprovalHistoryDTO> assignee;
}

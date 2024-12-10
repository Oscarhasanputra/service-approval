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


@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class AssigneeTransApprovalHistoryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -892670575546283244L;
    @Schema(description = "Id Assignee Approval",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String id;

    @Schema(description = "AssigneeID is userdi",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String assigneeId;

    @Schema(description = "Assignee Name of Approval",
            example = "user",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String assigneeName;

    @Schema(description = "Email Assignee Approval",
            example = "user@gmail.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String assigneeEmail;

    @Schema(description = "Whatasapp Number Assignee Approval",
            example = "0857xxxxxxxx",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String assigneeWhatsapp;

    @Schema(description = "Assignee Telegram Account",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String assigneeTelegram;

    @Schema(description = "Status to Send Email",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    private Boolean sendEmailNotification;

    @Schema(description = "Status to Send WA",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    private Boolean sendWANotification ;

    @Schema(description = "Status to Send Telegram",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    private Boolean sendTelegramNotification;


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

    @Schema(description = "Note Approval",
            example = "",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String approvalNote;
}

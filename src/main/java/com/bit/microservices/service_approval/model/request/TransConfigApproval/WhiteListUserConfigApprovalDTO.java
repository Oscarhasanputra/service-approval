package com.bit.microservices.service_approval.model.request.TransConfigApproval;

import com.bit.microservices.service_approval.enums.WhiteListUserEnum;
import com.bit.microservices.service_approval.utils.annotations.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class WhiteListUserConfigApprovalDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4684360836833654797L;

    @Schema(description = "ID of Whitelist User",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String id;

    @Schema(description = "Type of Event",
            example = "USER INTERNAL",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    @ValidEnum(enumClass = WhiteListUserEnum.class)
    private String whitelistuserType;

    @Schema(description = "ID of Sub event Config Approval",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String whitelistuserId;

    @Schema(description = "Type of Event",
            example = "User A",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Invalid Mandatory Field")
    @NotBlank(message = "Invalid Mandatory Field")
    private String whitelistuserAttribute;
}

package com.bit.microservices.service_approval.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
public class SearchRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4237891482631742120L;

    @Schema(description = "Application Code",
            example = "[]",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> keyword;

    @Schema(description = "Application Code",
            example = "LIST",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String requestType;

    @Schema(
            description = "Number Maximum of items to being displayed",
            example = "10",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Integer size;

    @Schema(
            description = "Number Maximum of items to being displayed",
            example = "1",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Integer page;

    @Schema(
            description = "Number Maximum of items to being displayed",
            example = "now()",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private OffsetDateTime startTime;

    @Schema(
            description = "Number Maximum of items to being displayed",
            example = "now()",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private OffsetDateTime endTime;

    @Schema(
            description = "Number Maximum of items to being displayed",
            example = "false",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Boolean isDeleted;


    @Schema(description = "Application Code",
            example = "[]",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> fieldNames;

    @Schema(description = "Application Code",
            example = "{}",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> sortBy;

    @Schema(description = "Application Code",
            example = "{}",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, List<String>> filterBy;

}

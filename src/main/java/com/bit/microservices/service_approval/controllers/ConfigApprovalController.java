package com.bit.microservices.service_approval.controllers;

import com.bit.microservices.service_approval.enums.ResponseStatusEnum;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.CreateTransConfigApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.DeleteTransConfigApprovalRequestDTO;
import com.bit.microservices.service_approval.model.response.MainResponseDTO;
import com.bit.microservices.service_approval.model.response.RequestResponseDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;
import com.bit.microservices.service_approval.services.ConfigApprovalService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/approval/configapproval")
@Validated
public class ConfigApprovalController {

    @Autowired
    private ConfigApprovalService configApprovalService;
    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/create")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<MainResponseDTO<List<ResultResponseDTO>>>>  createConfigApproval(
            @RequestHeader HttpHeaders headers,

            @Valid @RequestBody List<CreateTransConfigApprovalRequestDTO> requests
    ){
        return this.configApprovalService.createConfigApproval(requests).map((response)->{
            return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
        });

    }

    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/update")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<MainResponseDTO<List<ResultResponseDTO>>>> updateConfigApproval(
            @RequestHeader HttpHeaders headers,


            @Valid @RequestBody List<CreateTransConfigApprovalRequestDTO> requests
    ){

        return this.configApprovalService.updateConfigApproval(requests).map((response)->{
            return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
        });
    }

    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/delete")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<MainResponseDTO<List<ResultResponseDTO>>>> deleteConfigApproval(
            @RequestHeader HttpHeaders headers,
            @Valid @RequestBody List<DeleteTransConfigApprovalRequestDTO> requests
    ){
        return this.configApprovalService.deleteConfigApproval(requests).map((response)->{
            return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
        });
    }

    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/get")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<RequestResponseDTO<String>>> getConfigApproval(
            @RequestHeader HttpHeaders headers
    ){
        return Mono.just(ResponseEntity.ok(new RequestResponseDTO<>("","")));
    }

    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/get-list")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<ViewPagingResponseDTO>> getListConfigApproval(
            @RequestHeader HttpHeaders headers,
            @RequestBody SearchRequestDTO request
            ){
        System.out.println("get request tester nich");
        System.out.println(request);

        return this.configApprovalService.getConfigApproval(request).map((response)->{
            return ResponseEntity.ok(response);
        });
    }
}

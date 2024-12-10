package com.bit.microservices.service_approval.controllers;

import com.bit.microservices.service_approval.enums.ResponseStatusEnum;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.request.TransApprovalHistory.CreateTransApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.TransApprovalHistory.DeleteTransApprovalHistoryRequestDTO;
import com.bit.microservices.service_approval.model.response.MainResponseDTO;
import com.bit.microservices.service_approval.model.response.RequestResponseDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;
import com.bit.microservices.service_approval.services.ApprovalRequestService;
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
@RequestMapping("/approval/approvalrequest")
@Validated
public class ApprovalRequestController {

    @Autowired
    private ApprovalRequestService approvalRequestService;

    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/create")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<MainResponseDTO<List<ResultResponseDTO>>>>  createApprovalHistory(
            @RequestHeader HttpHeaders headers,

            @Valid @RequestBody List<CreateTransApprovalRequestDTO> requests
    ){


        return this.approvalRequestService.createApprovalRequest(requests).map((response)->{
            return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
        });

    }
    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/update")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<MainResponseDTO<List<ResultResponseDTO>>>> updateApprovalHistory(
            @RequestHeader HttpHeaders headers,
            @Valid @RequestBody List<CreateTransApprovalRequestDTO> requests
    ){
        return this.approvalRequestService.updateApprovalRequest(requests).map((response)->{
            return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
        });

    }

    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/delete")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<MainResponseDTO<List<ResultResponseDTO>>>> deleteApprovalHistory(
            @RequestHeader HttpHeaders headers,
            @Valid @RequestBody List<DeleteTransApprovalHistoryRequestDTO> requests
    ){
        return this.approvalRequestService.deleteApprovalRequest(requests).map((response)->{
            return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
        });

    }

    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/get")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<RequestResponseDTO<String>>> getApprovalHistory(
            @RequestHeader HttpHeaders headers
    ){
        return Mono.just(ResponseEntity.ok(new RequestResponseDTO<>("","")));
    }

    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/get-list")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<ViewPagingResponseDTO>>  getListApprovalHistory(
            @RequestHeader HttpHeaders headers,
            @RequestBody SearchRequestDTO request
            ){
        return this.approvalRequestService.getApprovalRequest(request).map((response)->{
            return ResponseEntity.ok(response);
        });
    }
}

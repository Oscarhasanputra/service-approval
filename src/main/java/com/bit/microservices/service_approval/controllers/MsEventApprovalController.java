package com.bit.microservices.service_approval.controllers;

import com.bit.microservices.service_approval.entity.MsEventApproval;
import com.bit.microservices.service_approval.enums.ResponseStatusEnum;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.CreateEventApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.DeleteEventApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.UpdateEventApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.RequestDetailDTO;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.response.MainResponseDTO;
import com.bit.microservices.service_approval.model.response.MasterEventApproval.MsEventApprovalDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import com.bit.microservices.service_approval.model.response.RequestResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewMainResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;
import com.bit.microservices.service_approval.services.MsEventApprovalService;
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
@RequestMapping("/approval/eventapproval")
@Validated
public class MsEventApprovalController {

    @Autowired
    private MsEventApprovalService msEventApprovalService;

    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/create")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<MainResponseDTO<List<ResultResponseDTO>>>> createEventApproval(
            @RequestHeader HttpHeaders headers,
            @Valid @RequestBody List<CreateEventApprovalRequestDTO> requests
    ){
        return this.msEventApprovalService.createMasterEventApproval(requests).map((response)->{
            return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
        });
    }


    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/update")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<MainResponseDTO<List<ResultResponseDTO>>>> updateEventApproval(
            @RequestHeader HttpHeaders headers,
            @Valid @RequestBody List<UpdateEventApprovalRequestDTO> requests
    ){


//        List<ResultResponseDTO> response = ;
        return this.msEventApprovalService.updateMasterEventApproval(requests).map((response)->{
            return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
        });

    }

    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/delete")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<MainResponseDTO<List<ResultResponseDTO>>>> deleteEventApproval(
            @RequestHeader HttpHeaders headers,
            @Valid @RequestBody List<DeleteEventApprovalRequestDTO> requests
    ){
        return this.msEventApprovalService.deleteMasterEventApproval(requests).map((response)->{
            return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
        });

    }


    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/get")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<ViewMainResponseDTO<MsEventApprovalDTO>>> getEventApproval(
            @RequestHeader HttpHeaders headers,
            @RequestBody RequestDetailDTO request
            ){
        return this.msEventApprovalService.getSingleEventApproval(request).map((data)->{
            return ResponseEntity.ok(data);
        });

    }

    @Operation(summary = "Get List Table Bigquery Name", description = "Get List of Table Bigquery Via Request", tags = { "Bigquery Table" } )
    @PostMapping("/v1/0/get-list")
    @PreAuthorize("hasAuthority('SCOPE_bhakti')")
    public Mono<ResponseEntity<ViewPagingResponseDTO>>  getListEventApproval(
            @RequestHeader HttpHeaders headers,
            @RequestBody SearchRequestDTO request
            ){
        return this.msEventApprovalService.getEventApproval(request).map((response)->{
            return ResponseEntity.ok(response);
        });
    }


}

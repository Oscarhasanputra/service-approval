package com.bit.microservices.service_approval.services;

import com.bit.microservices.service_approval.entity.MsEventApproval;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.CreateEventApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.DeleteEventApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.UpdateEventApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.RequestDetailDTO;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.response.MasterEventApproval.MsEventApprovalDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewMainResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MsEventApprovalService {

    public Mono<List<ResultResponseDTO>> createMasterEventApproval(List<CreateEventApprovalRequestDTO> requestDTO);


    public Mono<List<ResultResponseDTO>> updateMasterEventApproval(List<UpdateEventApprovalRequestDTO> requestDTO);


    public Mono<List<ResultResponseDTO>> deleteMasterEventApproval(List<DeleteEventApprovalRequestDTO> requestDTO);

    public Mono<ViewPagingResponseDTO> getEventApproval(SearchRequestDTO requestDTO);

    public Mono<ViewMainResponseDTO<MsEventApprovalDTO>> getSingleEventApproval(RequestDetailDTO requestDetailDTO);

}

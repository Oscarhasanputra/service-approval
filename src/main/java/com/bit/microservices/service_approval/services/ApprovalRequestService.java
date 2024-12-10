package com.bit.microservices.service_approval.services;

import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.request.TransApprovalHistory.CreateTransApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.TransApprovalHistory.DeleteTransApprovalHistoryRequestDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ApprovalRequestService {

    public Mono<List<ResultResponseDTO>> createApprovalRequest(List<CreateTransApprovalRequestDTO> requestDTO);

    public Mono<List<ResultResponseDTO>> updateApprovalRequest(List<CreateTransApprovalRequestDTO> requestDTO);


    public Mono<List<ResultResponseDTO>> deleteApprovalRequest(List<DeleteTransApprovalHistoryRequestDTO> requestList);

    public Mono<ViewPagingResponseDTO> getApprovalRequest(SearchRequestDTO requestDTO);

}

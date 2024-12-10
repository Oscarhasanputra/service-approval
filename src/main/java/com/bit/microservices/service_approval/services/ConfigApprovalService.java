package com.bit.microservices.service_approval.services;

import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.CreateTransConfigApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.DeleteTransConfigApprovalRequestDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ConfigApprovalService {

    public Mono<List<ResultResponseDTO>> createConfigApproval(List<CreateTransConfigApprovalRequestDTO> requestDTO);

    public Mono<List<ResultResponseDTO>> updateConfigApproval(List<CreateTransConfigApprovalRequestDTO> requestDTO);

    public Mono<List<ResultResponseDTO>> deleteConfigApproval(List<DeleteTransConfigApprovalRequestDTO> requestList);


    public Mono<ViewPagingResponseDTO> getConfigApproval(SearchRequestDTO requestDTO);
}

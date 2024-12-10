package com.bit.microservices.service_approval.repository;

import com.bit.microservices.model.bit.va.request.RequestPagingDTO;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.response.TransConfigApproval.ConfigApprovalDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;

import java.util.List;

public interface IQConfigApprovalRepository {

    public List<ConfigApprovalDTO> findConfigApproval(RequestPagingDTO request);


    public ViewPagingResponseDTO findConfigApproval(SearchRequestDTO request);
}

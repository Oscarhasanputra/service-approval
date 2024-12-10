package com.bit.microservices.service_approval.repository;

import com.bit.microservices.model.bit.va.request.RequestPagingDTO;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.response.TransApprovalHistory.ApprovalRequestDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;

import java.util.List;

public interface IQApprovalRequestRepository {

    public List<ApprovalRequestDTO> findApprovalRequest(RequestPagingDTO request);


    public ViewPagingResponseDTO findApprovalRequest(SearchRequestDTO request);
}

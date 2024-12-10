package com.bit.microservices.service_approval.repository;

import com.bit.microservices.model.bit.va.request.RequestPagingDTO;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.response.MasterEventApproval.MsEventApprovalDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;

import java.util.List;

public interface IQMsEventApprovalRepository {


    public List<MsEventApprovalDTO> findEventApproval(RequestPagingDTO request);
    public ViewPagingResponseDTO findEventApproval(SearchRequestDTO request);
}

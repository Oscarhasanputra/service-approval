package com.bit.microservices.service_approval.services.graphql;

import com.bit.microservices.model.bit.va.request.RequestPagingDTO;
import com.bit.microservices.service_approval.entity.ConfigApprovalTrx;
import com.bit.microservices.service_approval.mapper.ConfigApprovalMapper;
import com.bit.microservices.service_approval.model.ConfigApprovalGraphqlDto;
import com.bit.microservices.service_approval.model.response.MasterEventApproval.MsEventApprovalDTO;
import com.bit.microservices.service_approval.model.response.TransConfigApproval.ConfigApprovalDTO;
import com.bit.microservices.service_approval.repository.ConfigApprovalTrxRepository;
import com.bit.microservices.service_approval.repository.IQConfigApprovalRepository;
import com.bit.microservices.service_approval.repository.IQMsEventApprovalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TestGraphqlService {

    @Autowired
    private ConfigApprovalTrxRepository configApprovalTrxRepository;

    @Autowired
    private IQConfigApprovalRepository iqConfigApprovalRepository;

    @Autowired
    private IQMsEventApprovalRepository iqMsEventApprovalRepository;

    @Autowired
    private ConfigApprovalMapper configApprovalMapper;
    public List<ConfigApprovalDTO> getListConfigApproval(RequestPagingDTO requestPagingDTO){
        return this.iqConfigApprovalRepository.findConfigApproval(requestPagingDTO);
//        List<ConfigApprovalTrx> configApprovalTrxList = this.configApprovalTrxRepository.findAll();
//        return this.configApprovalMapper.entityListToGraphqlDtoList(configApprovalTrxList);
    }

    public List<MsEventApprovalDTO> getListMsEventApproval(RequestPagingDTO requestPagingDTO){
        return this.iqMsEventApprovalRepository.findEventApproval(requestPagingDTO);
    }
}

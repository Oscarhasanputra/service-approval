package com.bit.microservices.service_approval.mapper;

import com.bit.microservices.model.BaseMapper;
import com.bit.microservices.service_approval.entity.ApprovalRequestLevelAssigneeTrc;
import com.bit.microservices.service_approval.entity.ApprovalRequestLevelTrc;
import com.bit.microservices.service_approval.entity.ApprovalRequestTrx;
import com.bit.microservices.service_approval.model.request.TransApprovalHistory.AssigneeTransApprovalHistoryDTO;
import com.bit.microservices.service_approval.model.request.TransApprovalHistory.CreateTransApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.TransApprovalHistory.LevelTransApprovalHistoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ApprovalHistoryMapper extends BaseMapper {
   ApprovalHistoryMapper INSTANCE = Mappers.getMapper(ApprovalHistoryMapper.class);



   @Mappings({
           @Mapping(source = "configApprovalId", target = "trxConfigapprovalId"),
           @Mapping(source = "branchId", target = "msBranchId"),
           @Mapping(source = "branchCode", target = "msBranchCode"),
           @Mapping(target = "status",ignore = true)
   })
   ApprovalRequestTrx historyDtoToEntity(CreateTransApprovalRequestDTO requestDTO);

   ApprovalRequestLevelTrc levelHistoryDtoToEntity(LevelTransApprovalHistoryDTO request);


   @Mappings({
           @Mapping(source = "sendWANotification", target = "sendWaNotification")
   })
   ApprovalRequestLevelAssigneeTrc levelHistoryAssigneeDtoToEntity(AssigneeTransApprovalHistoryDTO request);
}

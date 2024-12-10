package com.bit.microservices.service_approval.mapper;

import com.bit.microservices.model.BaseMapper;
import com.bit.microservices.service_approval.entity.MsEventApproval;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.CreateEventApprovalRequestDTO;
import com.bit.microservices.service_approval.model.response.MasterEventApproval.MsEventApprovalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MsEventApprovalMapper extends BaseMapper {
    MsEventApprovalMapper INSTANCE = Mappers.getMapper(MsEventApprovalMapper.class);


    @Mappings({
            @Mapping(source = "active", target = "isActive")
    })
    MsEventApproval dtoToEntity(CreateEventApprovalRequestDTO requestDTO);

    @Mappings({
            @Mapping(source = "isDeleted", target = "deleted")

    })
    MsEventApprovalDTO entityToDto(MsEventApproval entity);
}

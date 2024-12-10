package com.bit.microservices.service_approval.mapper;

import com.bit.microservices.model.BaseMapper;
import com.bit.microservices.service_approval.entity.ConfigApprovalSubEventAssigneeTrc;
import com.bit.microservices.service_approval.entity.ConfigApprovalSubEventTrc;
import com.bit.microservices.service_approval.entity.ConfigApprovalSubEventWhiteListUserTrc;
import com.bit.microservices.service_approval.entity.ConfigApprovalTrx;
import com.bit.microservices.service_approval.enums.WhiteListUserEnum;
import com.bit.microservices.service_approval.model.ConfigApprovalGraphqlDto;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.AssigneeSubEventTransConfigDTO;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.CreateTransConfigApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.SubEventTransConfigApprovalDTO;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.WhiteListUserConfigApprovalDTO;
import com.bit.microservices.service_approval.model.response.TransConfigApproval.ConfigApprovalSubeventAssigneeDTO;
import com.bit.microservices.service_approval.model.response.TransConfigApproval.ConfigApprovalSubeventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ConfigApprovalMapper extends BaseMapper {
    ConfigApprovalMapper INSTANCE = Mappers.getMapper(ConfigApprovalMapper.class);

    @Mappings({
            @Mapping(source = "eventApprovalId", target = "msEventapprovalId"),
            @Mapping(source = "eventApprovalCode", target = "msEventapprovalCode"),
            @Mapping(source = "branchId", target = "msBranchId"),
            @Mapping(source = "branchCode", target = "msBranchCode")
    })
    ConfigApprovalTrx dtoToEntityConfigApproval(CreateTransConfigApprovalRequestDTO requestDTO);

    ConfigApprovalSubEventTrc subEventDtoToentityConfigApproval(SubEventTransConfigApprovalDTO request);

    ConfigApprovalSubEventAssigneeTrc subEventAssigneeDtoToEntityAssigneeApproval(AssigneeSubEventTransConfigDTO dto);

    @Mappings({
            @Mapping(source = "whitelistuserAttribute", target = "whitelistuserCode"),

    })
    ConfigApprovalSubEventWhiteListUserTrc subEventWhiteListUserDtoToEntityWhiteListUserApproval(WhiteListUserConfigApprovalDTO dto);


    ConfigApprovalGraphqlDto entityToGraphqlDto(ConfigApprovalTrx dto);

    List<ConfigApprovalGraphqlDto> entityListToGraphqlDtoList(List<ConfigApprovalTrx> dto);

    ConfigApprovalSubeventDTO entitySubeventToConfigSubEventDTO(ConfigApprovalSubEventTrc dto);

    ConfigApprovalSubeventAssigneeDTO entityAssigneeToConfigSubEventAssigneeDTO(ConfigApprovalSubEventAssigneeTrc dto);


    @Named("toOffsetDateTime")
    default OffsetDateTime map(LocalDateTime time){
        return time.atOffset(ZoneOffset.UTC);
    }


    default WhiteListUserEnum map(String statusUser){
        try {
            return WhiteListUserEnum.getValue(statusUser);
        }catch (Exception err){
            return null;
        }
    }
}

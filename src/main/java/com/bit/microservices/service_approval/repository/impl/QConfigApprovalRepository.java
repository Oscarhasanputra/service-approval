package com.bit.microservices.service_approval.repository.impl;

import com.bit.microservices.model.bit.va.*;
import com.bit.microservices.model.bit.va.request.RequestPagingDTO;
import com.bit.microservices.service_approval.entity.ConfigApprovalSubEventAssigneeTrc;
import com.bit.microservices.service_approval.entity.ConfigApprovalSubEventTrc;
import com.bit.microservices.service_approval.enums.ResponseStatusEnum;
import com.bit.microservices.service_approval.mapper.ConfigApprovalMapper;
import com.bit.microservices.service_approval.model.GeneratedDslDto;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.response.TransConfigApproval.ConfigApprovalDTO;
import com.bit.microservices.service_approval.model.response.TransConfigApproval.ConfigApprovalSubeventAssigneeDTO;
import com.bit.microservices.service_approval.model.response.TransConfigApproval.ConfigApprovalSubeventDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;
import com.bit.microservices.service_approval.repository.ConfigApprovalSubEventAssigneeTrcRepository;
import com.bit.microservices.service_approval.repository.ConfigApprovalSubEventTrcRepository;
import com.bit.microservices.service_approval.repository.IQConfigApprovalRepository;
import com.bit.microservices.service_approval.services.utility.QueryService;
import com.bit.microservices.service_approval.services.utility.UtilService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class QConfigApprovalRepository implements IQConfigApprovalRepository {

    @Autowired
    private UtilService utilService;

    @Autowired
    private QueryService queryService;

    @PersistenceContext()
    private EntityManager em;

    @Autowired
    private ConfigApprovalSubEventTrcRepository configApprovalSubEventTrcRepository;

    @Autowired
    private ConfigApprovalSubEventAssigneeTrcRepository configApprovalSubEventAssigneeTrcRepository;

    @Autowired
    private ConfigApprovalMapper configApprovalMapper;

    @Override
    public List<ConfigApprovalDTO> findConfigApproval(RequestPagingDTO request) {

        Pageable page = this.utilService.pageableUtils(request);

        GeneratedDslDto dslDto = this.utilService.generateQueryMetaData(ConfigApprovalDTO.class);



        //pass by reference
        Set<String> select = dslDto.getSelects();
        //join entity and grouping;

        this.utilService.setConditionQuery(dslDto,request);



        // if order is available;
        String queryBasePage = "select "+dslDto.getSelects().stream().collect(Collectors.joining(",")) +
                " from trx_configapproval ";
        queryBasePage = queryBasePage+" where "+dslDto.getWhere() +" group by "+dslDto.getGroups().stream().collect(Collectors.joining(","))+" having "+dslDto.getHaving();



        Page<ConfigApprovalDTO> configApprovalList = this.utilService.find(queryBasePage,dslDto,page);
        List<String> configApprovalIds = configApprovalList.getContent().stream().map((configApproval)->configApproval.getId()).toList();

        List<ConfigApprovalSubEventTrc> configApprovalSubEventTrcList= this.configApprovalSubEventTrcRepository.findByTrxConfigapprovalIdIn(configApprovalIds);
        List<String> subEventIds = new ArrayList<>();
        Map<String,List<ConfigApprovalSubeventDTO>> mapApprovalSubEvent= new HashMap<>();
        configApprovalSubEventTrcList.stream().forEach((subevent)->{
            subEventIds.add(subevent.getId());
            ConfigApprovalSubeventDTO configApprovalSubeventDTO =  this.configApprovalMapper.entitySubeventToConfigSubEventDTO(subevent);
            List<ConfigApprovalSubeventDTO> listTemp = Objects.isNull(mapApprovalSubEvent.get(configApprovalSubeventDTO.getTrxConfigapprovalId()))?new ArrayList<>():mapApprovalSubEvent.get(configApprovalSubeventDTO.getTrxConfigapprovalId());
            listTemp.add(configApprovalSubeventDTO);
            mapApprovalSubEvent.put(subevent.getTrxConfigapprovalId(),listTemp);
        });

        List<ConfigApprovalSubEventAssigneeTrc> subEventAssigneeTrcList = this.configApprovalSubEventAssigneeTrcRepository.findByTrcConfigapprovalsubeventIdIn(subEventIds);
        Map<String,List<ConfigApprovalSubeventAssigneeDTO>> mapSubeventAssignee = new HashMap<>();
        subEventAssigneeTrcList.stream().forEach((assignee)->{
            List<ConfigApprovalSubeventAssigneeDTO> listTemp = Objects.isNull(mapSubeventAssignee.get(assignee.getTrcConfigapprovalsubeventId()))?new ArrayList<>():mapSubeventAssignee.get(assignee.getTrcConfigapprovalsubeventId());
            ConfigApprovalSubeventAssigneeDTO dto = this.configApprovalMapper.entityAssigneeToConfigSubEventAssigneeDTO(assignee);
            listTemp.add(dto);
            mapSubeventAssignee.put(assignee.getTrcConfigapprovalsubeventId(),listTemp);
        });


        configApprovalList.getContent().stream().forEach((configApprovalDTO -> {
            List<ConfigApprovalSubeventDTO> subeventDTOList = Objects.isNull(mapApprovalSubEvent.get(configApprovalDTO.getId()))?new ArrayList<>():mapApprovalSubEvent.get(configApprovalDTO.getId());
            subeventDTOList.forEach((subEvent)->{
                List<ConfigApprovalSubeventAssigneeDTO> subeventAssigneeDTOList = Objects.isNull(mapSubeventAssignee.get(subEvent.getId()))? new ArrayList<>():mapSubeventAssignee.get(subEvent.getId());
                subEvent.setAssignee(subeventAssigneeDTOList);
            });
            configApprovalDTO.setSubevent(subeventDTOList);
        }));

        return configApprovalList.getContent();

    }

    @Override
    public ViewPagingResponseDTO findConfigApproval(SearchRequestDTO request) {

        Pageable page = this.queryService.pageableUtils(request);

        GeneratedDslDto dslDto = this.queryService.generateQueryMetaData(ConfigApprovalDTO.class);

        //pass by reference
        List<WhereConditionDTO> whereConditionDTO1 = new ArrayList<>();

        request.getKeyword().forEach((keyword)->{
            List<WhereConditionDTO> orCondition = new ArrayList<>();
            request.getFieldNames().forEach((fieldName)->{
                WhereConditionDTO temp = new WhereConditionDTO();
                temp.setConjunction(ConjunctionConditionEnum.OR.name());
                temp.setCondition(ConditionQueryEnum.LIKE.name());
                temp.setField(fieldName);
                temp.setValue("%"+keyword+"%");
                orCondition.add(temp);
            });
            InnerCondition innerCondition = new InnerCondition();
            innerCondition.setConjunction(ConjunctionConditionEnum.OR.name());
            innerCondition.setWhere(orCondition);
            WhereConditionDTO innerWhere = new WhereConditionDTO();
            innerWhere.setInner(innerCondition);
            innerWhere.setConjunction(ConjunctionConditionEnum.OR.name());
            whereConditionDTO1.add(innerWhere);
        });

        List<WhereConditionDTO> whereConditionDTO2 = new ArrayList<>();
        for (Map.Entry<String, List<String>> filterMap : request.getFilterBy().entrySet()) {
            String fieldName = filterMap.getKey();

            List<WhereConditionDTO> andCondition = new ArrayList<>();

            List<String> listValue = filterMap.getValue();
            for (String value : listValue) {
                WhereConditionDTO temp = new WhereConditionDTO();
                temp.setConjunction(ConjunctionConditionEnum.OR.name());
                temp.setCondition(ConditionQueryEnum.LIKE.name());
                temp.setField(fieldName);
                temp.setValue("%"+value+"%");
                andCondition.add(temp);
            }
            InnerCondition innerCondition = new InnerCondition();
            innerCondition.setConjunction(ConjunctionConditionEnum.AND.name());
            innerCondition.setWhere(andCondition);
            WhereConditionDTO innerWhere = new WhereConditionDTO();
            innerWhere.setInner(innerCondition);
            innerWhere.setConjunction(ConjunctionConditionEnum.AND.name());
            whereConditionDTO2.add(innerWhere);
        }

        InnerCondition innerCondition1 = new InnerCondition();
        innerCondition1.setConjunction(ConjunctionConditionEnum.AND.name());
        innerCondition1.setWhere(whereConditionDTO1);
        WhereConditionDTO whereConditionFinal1 = new WhereConditionDTO();
        whereConditionFinal1.setInner(innerCondition1);
        whereConditionFinal1.setConjunction(ConjunctionConditionEnum.AND.name());


        InnerCondition innerCondition2 = new InnerCondition();
        innerCondition2.setConjunction(ConjunctionConditionEnum.AND.name());
        innerCondition2.setWhere(whereConditionDTO2);

        WhereConditionDTO whereConditionFinal2 = new WhereConditionDTO();
        whereConditionFinal2.setInner(innerCondition2);
        whereConditionFinal2.setConjunction(ConjunctionConditionEnum.AND.name());


        List<WhereConditionDTO> joinCondition = new ArrayList<>();


        joinCondition.add(whereConditionFinal1);

        joinCondition.add(whereConditionFinal2);

        this.queryService.setConditionQuery(dslDto,joinCondition);



        // if order is available;
        String queryBasePage = "select "+dslDto.getSelects().stream().collect(Collectors.joining(",")) +
                " from trx_configapproval ";
        queryBasePage = queryBasePage+" where "+dslDto.getWhere() +" group by "+dslDto.getGroups().stream().collect(Collectors.joining(","))+" having "+dslDto.getHaving();


        Page<ConfigApprovalDTO> configApprovalList = this.queryService.find(queryBasePage,dslDto,request,page);
        List<String> configApprovalIds = configApprovalList.getContent().stream().map((configApproval)->configApproval.getId()).toList();

        List<ConfigApprovalSubEventTrc> configApprovalSubEventTrcList= this.configApprovalSubEventTrcRepository.findByTrxConfigapprovalIdIn(configApprovalIds);
        List<String> subEventIds = new ArrayList<>();
        Map<String,List<ConfigApprovalSubeventDTO>> mapApprovalSubEvent= new HashMap<>();
        configApprovalSubEventTrcList.stream().forEach((subevent)->{
            subEventIds.add(subevent.getId());
            ConfigApprovalSubeventDTO configApprovalSubeventDTO =  this.configApprovalMapper.entitySubeventToConfigSubEventDTO(subevent);
            List<ConfigApprovalSubeventDTO> listTemp = Objects.isNull(mapApprovalSubEvent.get(configApprovalSubeventDTO.getTrxConfigapprovalId()))?new ArrayList<>():mapApprovalSubEvent.get(configApprovalSubeventDTO.getTrxConfigapprovalId());
            listTemp.add(configApprovalSubeventDTO);
            mapApprovalSubEvent.put(subevent.getTrxConfigapprovalId(),listTemp);
        });

        List<ConfigApprovalSubEventAssigneeTrc> subEventAssigneeTrcList = this.configApprovalSubEventAssigneeTrcRepository.findByTrcConfigapprovalsubeventIdIn(subEventIds);
        Map<String,List<ConfigApprovalSubeventAssigneeDTO>> mapSubeventAssignee = new HashMap<>();
        subEventAssigneeTrcList.stream().forEach((assignee)->{
            List<ConfigApprovalSubeventAssigneeDTO> listTemp = Objects.isNull(mapSubeventAssignee.get(assignee.getTrcConfigapprovalsubeventId()))?new ArrayList<>():mapSubeventAssignee.get(assignee.getTrcConfigapprovalsubeventId());
            ConfigApprovalSubeventAssigneeDTO dto = this.configApprovalMapper.entityAssigneeToConfigSubEventAssigneeDTO(assignee);
            listTemp.add(dto);
            mapSubeventAssignee.put(assignee.getTrcConfigapprovalsubeventId(),listTemp);
        });

        configApprovalList.getContent().stream().forEach((configApprovalDTO -> {
            List<ConfigApprovalSubeventDTO> subeventDTOList = Objects.isNull(mapApprovalSubEvent.get(configApprovalDTO.getId()))?new ArrayList<>():mapApprovalSubEvent.get(configApprovalDTO.getId());
            subeventDTOList.forEach((subEvent)->{
                List<ConfigApprovalSubeventAssigneeDTO> subeventAssigneeDTOList = Objects.isNull(mapSubeventAssignee.get(subEvent.getId()))? new ArrayList<>():mapSubeventAssignee.get(subEvent.getId());
                subEvent.setAssignee(subeventAssigneeDTOList);
            });
            configApprovalDTO.setSubevent(subeventDTOList);
        }));

        return new ViewPagingResponseDTO(ResponseStatusEnum.SUCCESS.name(),ResponseStatusEnum.SUCCESS.code,ResponseStatusEnum.SUCCESS.responseCode,"Config Approval Get List Successfully", configApprovalList);

    }
}

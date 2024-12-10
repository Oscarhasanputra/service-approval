package com.bit.microservices.service_approval.repository.impl;

import com.bit.microservices.model.bit.va.ConditionQueryEnum;
import com.bit.microservices.model.bit.va.ConjunctionConditionEnum;
import com.bit.microservices.model.bit.va.InnerCondition;
import com.bit.microservices.model.bit.va.WhereConditionDTO;
import com.bit.microservices.model.bit.va.request.RequestPagingDTO;
import com.bit.microservices.service_approval.enums.ResponseStatusEnum;
import com.bit.microservices.service_approval.model.GeneratedDslDto;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.response.MasterEventApproval.MsEventApprovalDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;
import com.bit.microservices.service_approval.repository.IQMsEventApprovalRepository;
import com.bit.microservices.service_approval.services.utility.QueryService;
import com.bit.microservices.service_approval.services.utility.UtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class QMsEventApprovalRepository implements IQMsEventApprovalRepository {
    @Autowired
    private UtilService utilService;

    @Autowired
    private QueryService queryService;

    @Override
    public List<MsEventApprovalDTO> findEventApproval(RequestPagingDTO request) {
        Pageable page = this.utilService.pageableUtils(request);

        GeneratedDslDto dslDto = this.utilService.generateQueryMetaData(MsEventApprovalDTO.class);



        //pass by reference
        Set<String> select = dslDto.getSelects();
        //join entity and grouping;

        this.utilService.setConditionQuery(dslDto,request);



        // if order is available;
        String queryBasePage = "select "+dslDto.getSelects().stream().collect(Collectors.joining(",")) +
                " from ms_eventapproval ";
        queryBasePage = queryBasePage+" where "+dslDto.getWhere() +" group by "+dslDto.getGroups().stream().collect(Collectors.joining(","))+" having "+dslDto.getHaving();

        Page<MsEventApprovalDTO> msEventApprovalDTOS = this.utilService.find(queryBasePage,dslDto,page);

        return msEventApprovalDTOS.getContent();
    }

    @Override
    public ViewPagingResponseDTO findEventApproval(SearchRequestDTO request) {

        Pageable page = this.queryService.pageableUtils(request);

        GeneratedDslDto dslDto = this.queryService.generateQueryMetaData(MsEventApprovalDTO.class);


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
                " from ms_eventapproval ";
        queryBasePage = queryBasePage+" where "+dslDto.getWhere() +" group by "+dslDto.getGroups().stream().collect(Collectors.joining(","))+" having "+dslDto.getHaving();


        Page<MsEventApprovalDTO> msEventApprovalDTOS = this.queryService.find(queryBasePage,dslDto,request,page);


        return new ViewPagingResponseDTO(ResponseStatusEnum.SUCCESS.name(),ResponseStatusEnum.SUCCESS.code,"-","Event Approval Get List Successfully", msEventApprovalDTOS);
//        return configApprovalList;
    }
}

package com.bit.microservices.service_approval.controllers.graphql;

import com.bit.microservices.model.bit.va.request.RequestPagingDTO;
import com.bit.microservices.service_approval.entity.ConfigApprovalTrx;
import com.bit.microservices.service_approval.model.ConfigApprovalGraphqlDto;
import com.bit.microservices.service_approval.model.response.MasterEventApproval.MsEventApprovalDTO;
import com.bit.microservices.service_approval.model.response.TransConfigApproval.ConfigApprovalDTO;
import com.bit.microservices.service_approval.services.graphql.TestGraphqlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    @Autowired
    private TestGraphqlService testGraphqlService;

    @QueryMapping
    public Mono<List<ConfigApprovalDTO>> getConfigApproval(@Argument RequestPagingDTO filter){

        return Mono.just(testGraphqlService.getListConfigApproval(filter));
    };

    @QueryMapping
    @PutMapping("/get-list")
    public Mono<List<MsEventApprovalDTO>> getEventApproval(@Argument RequestPagingDTO filter){

        return Mono.just(testGraphqlService.getListMsEventApproval(filter));
    };


    @QueryMapping
    public Mono<List<MsEventApprovalDTO>> getEventApprovalLvl2(@Argument RequestPagingDTO filter){

        return Mono.just(testGraphqlService.getListMsEventApproval(filter));
    };
}

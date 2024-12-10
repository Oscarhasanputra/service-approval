package com.bit.microservices.service_approval.util;

import com.bit.microservices.service_approval.mapper.*;
import com.bit.microservices.service_approval.mapper.ApprovalHistoryMapperImpl;
import com.bit.microservices.service_approval.mapper.ConfigApprovalMapperImpl;
import com.bit.microservices.service_approval.mapper.MsEventApprovalMapperImpl;
import com.bit.microservices.service_approval.repository.MsEventApprovalRepository;
import org.hibernate.SessionFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@TestConfiguration
public class ContextConfigurationTest {
    @Bean
    MsEventApprovalMapper msEventApprovalMapper(){
        return new MsEventApprovalMapperImpl();
    }

    @Bean
    ConfigApprovalMapper configApprovalMapper(){
        return new ConfigApprovalMapperImpl();
    }

    @Bean
    ApprovalHistoryMapper approvalHistoryMapper(){
        return new ApprovalHistoryMapperImpl();
    }

    @Bean
    MsEventApprovalRepository msEventApprovalRepository(){
        return Mockito.mock(MsEventApprovalRepository.class);
    }
}

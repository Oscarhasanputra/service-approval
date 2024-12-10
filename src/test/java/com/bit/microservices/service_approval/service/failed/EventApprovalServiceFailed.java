package com.bit.microservices.service_approval.service.failed;

import com.bit.microservices.service_approval.entity.MsEventApproval;
import com.bit.microservices.service_approval.exceptions.BadRequestException;
import com.bit.microservices.service_approval.exceptions.InternalServerErrorException;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.CreateEventApprovalRequestDTO;
import com.bit.microservices.service_approval.repository.MsEventApprovalRepository;
import com.bit.microservices.service_approval.services.MsEventApprovalService;
import com.bit.microservices.service_approval.services.impl.MsEventApprovalServiceImpl;

import com.bit.microservices.service_approval.util.HibernateConfiguration;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {HibernateConfiguration.class})
public class EventApprovalServiceFailed {


    @InjectMocks
    private MsEventApprovalServiceImpl msEventApprovalService;


    @Mock
    private MsEventApprovalRepository msEventApprovalRepository;

    @Autowired
    private SessionFactory sessionFactoryBean;

    @Mock
    private SessionFactory sessionFactoryTest;


    @Autowired
    private RedissonClient redisson;


    @Before
    public void setupMock(){
        Session session1 = sessionFactoryBean.openSession();

        when(sessionFactoryTest.openSession()).thenReturn(session1);
        when(sessionFactoryTest.getCurrentSession()).thenReturn(session1);

    }

    @Test
    public void testSessionFactory() {
        System.out.println("session factory nech");
        System.out.println(this.sessionFactoryBean);
        this.sessionFactoryBean.openSession();
        System.out.println("running nech 2");
//        Mockito.mock
        System.out.println("running test");
    }

    @DisplayName("Integration Test With Approval Already Exists")
    @Test
    public void failedCreateEventApprovalAlreadyExist() throws Exception {
//        when(sessionFactory.openSession()).thenReturn(mock(Session.class));


        String requestBody = """
                [
                	{
                		"applicationId": "MC~uuid",
                		"applicationCode": "MC",
                		"code": "MC-CL",
                		"name": "REQUEST NAIK LIMIT MITRA",
                		"active": true,
                		"remarks": ""
                	},
                	{
                		"applicationId": "MC~uuid",
                		"applicationCode": "MC",
                		"code": "MC-UNLOCK",
                		"name": "UNLOCK MITRA TERKUNCI",
                		"active": true,
                		"remarks": ""
                	}
                ]
                """;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<CreateEventApprovalRequestDTO> request = objectMapper.readValue(requestBody, new TypeReference<List<CreateEventApprovalRequestDTO>>() {
        });


        MsEventApproval msEventApproval = new MsEventApproval();
        msEventApproval.setCode("MC-CL");
        msEventApproval.setIsDeleted(false);
        when(msEventApprovalRepository.findByCodeIn(any(Collection.class))).thenReturn(Arrays.asList(msEventApproval));
        try{
            Map<String,Object> userData = new HashMap<>();
            userData.put("userEmail","user@test.com");
            userData.put("userId","01");
            StepVerifier.create(msEventApprovalService.createMasterEventApproval(request).contextWrite(ctx-> ctx.put(HttpHeaders.AUTHORIZATION,userData)))
                    .expectError(BadRequestException.class)
                    .verify();

        }catch (Exception err){
            assert(false);
        }

    }

    @DisplayName("JUnit Test Failed Service")
    @Test
    public void failedCreateEventApprovalInvalid() throws Exception {
//        when(sessionFactory.openSession()).thenReturn(mock(Session.class));


        String requestBody = """
                [
                	{
                		"applicationId": null,
                		"applicationCode": "MC",
                		"code": "MC-CL",
                		"name": "REQUEST NAIK LIMIT MITRA",
                		"active": true,
                		"remarks": ""
                	},
                	{
                		"applicationId": "MC~uuid",
                		"applicationCode": "MC",
                		"code": "MC-UNLOCK",
                		"name": "UNLOCK MITRA TERKUNCI",
                		"active": true,
                		"remarks": ""
                	}
                ]
                """;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<CreateEventApprovalRequestDTO> request = objectMapper.readValue(requestBody, new TypeReference<List<CreateEventApprovalRequestDTO>>() {
        });


        MsEventApproval msEventApproval = new MsEventApproval();
        msEventApproval.setCode("MC-CL");
        msEventApproval.setIsDeleted(true);
        when(msEventApprovalRepository.findByCodeIn(any(Collection.class))).thenReturn(Arrays.asList(msEventApproval));
        try{
            Map<String,Object> userData = new HashMap<>();
            userData.put("userEmail","user@test.com");
            userData.put("userId","01");
            StepVerifier.create(msEventApprovalService.createMasterEventApproval(request).contextWrite(ctx-> ctx.put(HttpHeaders.AUTHORIZATION,userData)))
                    .expectError(InternalServerErrorException.class)
                    .verify();

        }catch (Exception err){
            assert(false);
        }

    }


}

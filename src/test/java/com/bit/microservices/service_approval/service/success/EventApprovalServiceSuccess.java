package com.bit.microservices.service_approval.service.success;

import com.bit.microservices.service_approval.entity.MsEventApproval;
import com.bit.microservices.service_approval.mapper.MsEventApprovalMapper;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.CreateEventApprovalRequestDTO;
import com.bit.microservices.service_approval.repository.MsEventApprovalRepository;
import com.bit.microservices.service_approval.services.impl.MsEventApprovalServiceImpl;
import com.bit.microservices.service_approval.util.ContextConfigurationTest;
//import com.bit.microservices.service_approval.util.HibernateConfiguration;
import com.bit.microservices.service_approval.util.HibernateConfiguration;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {HibernateConfiguration.class, ContextConfigurationTest.class})
public class EventApprovalServiceSuccess {


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
    @Autowired
    MsEventApprovalMapper msEventApprovalMapperBean;
    @Mock
    MsEventApprovalMapper msEventApprovalMapper;

    @Before
    public void setupMock(){
        Session session1 = sessionFactoryBean.openSession();

        when(sessionFactoryTest.openSession()).thenReturn(session1);
        when(sessionFactoryTest.getCurrentSession()).thenReturn(session1);
//        msEventApprovalMapperMock = msEventApprovalMapperBean;
//        when(msEventApprovalMapperMock).thenReturn(msEventApprovalMapperBean);
    }


    @DisplayName("JUnit Test Success Service")
    @Test
    public void createEventApprovalValid() throws Exception {
//        when(sessionFactory.openSession()).thenReturn(mock(Session.class));


        String requestBody = """
                [
                	{
                		"applicationId": "MC~uuuid",
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
            CreateEventApprovalRequestDTO requestDTO = request.get(0);

            MsEventApproval msEventApproval2 = msEventApprovalMapperBean.dtoToEntity(requestDTO);
//            msEventApproval2.setCode(requestDTO.getCode());
//            msEventApproval2.setMsApplicationId(requestDTO.get);
            msEventApproval2.setCreatedBy("userTest@gmail.com(userid)");
            msEventApproval2.setModifiedBy("userTest@gmail.com(userid)");
            msEventApproval2.setCreatedDate(LocalDateTime.now());
            msEventApproval2.setModifiedDate(LocalDateTime.now());
            when(msEventApprovalMapper.dtoToEntity(any(CreateEventApprovalRequestDTO.class))).thenReturn(msEventApproval2);

            StepVerifier.create(msEventApprovalService.createMasterEventApproval(request).contextWrite(ctx-> ctx.put(HttpHeaders.AUTHORIZATION,userData)
                    ))
                    .expectNextCount(1)
                    .expectComplete()
                    .verify();

        }catch (Exception err){
            assert(false);
        }

    }

}

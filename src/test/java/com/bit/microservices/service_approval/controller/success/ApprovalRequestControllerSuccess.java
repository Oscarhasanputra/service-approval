package com.bit.microservices.service_approval.controller.success;

import com.bit.microservices.service_approval.controller.AbstractConnectionContainer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;

import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:bootstrap.yml")
@AutoConfigureWebTestClient
public class ApprovalRequestControllerSuccess extends AbstractConnectionContainer {

    @Autowired
    private WebTestClient webTestClient;






    @Test
    public void testCreateApprovalRequest() throws Exception {

        String requestBody = """
                [
                  {
                    "id": "REQ/AUTONUMBER2",
                    "date": "2024-11-28T06:44:41.066Z",
                    "expiredDate": "2024-11-28T06:44:41.066Z",
                    "configApprovalId": "COF/AUTONUMBER",
                    "code": "CL/JKT/2410/000001",
                    "remarks": "string",
                    "branchId": "JKT",
                    "branchCode": "JKT",
                    "stopWhenRejected": true,
                    "frontendView": "hutang-piutang/creditlimit/view/mitra?",
                    "endpointOnFinished": "hutang-piutang/creditlimit/get",
                    "status": "WAITING FOR APPROVAL",
                    "level": [
                      {
                        "id": "0",
                        "level": "1",
                        "totalApprovalNeeded": "3",
                        "sendNotificationDate": "2024-11-28T06:44:41.066Z",
                        "approvalStatus": "WAITING FOR APPROVAL",
                        "approvalDate": "2024-11-28T06:44:41.066Z",
                        "approvalNote": "string",
                        "assignee": [
                          {
                            "id": "0",
                            "assigneeId": "1",
                            "assigneeName": "user",
                            "assigneeEmail": "user@gmail.com",
                            "assigneeWhatsapp": "0857xxxxxxxx",
                            "assigneeTelegram": "1",
                            "sendEmailNotification": true,
                            "sendWANotification": true,
                            "sendTelegramNotification": true,
                            "approvalStatus": "WAITING FOR APPROVAL",
                            "approvalDate": "2024-11-28T06:44:41.066Z",
                            "approvalNote": "string"
                          }
                        ]
                      }
                    ]
                  }
                ]
                """;


        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/approvalrequest/v1/0/create")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
//                    header.setBearerAuth(jwtAuthentication.getToken().getTokenValue());
                }).exchange().
                expectBody()
                        .jsonPath("$.status").isEqualTo("Success");

    }

    @Test
    public void testUpdateApprovalRequest() throws Exception {

        String requestBody = """
                [
                  {
                    "id": "REQ/AUTONUMBER",
                    "date": "2024-11-28T07:31:27.118Z",
                    "expiredDate": "2024-11-28T07:31:27.118Z",
                    "configApprovalId": "COF/AUTONUMBER",
                    "code": "CL/JKT/2410/000001",
                    "remarks": "string",
                    "branchId": "JKT",
                    "branchCode": "JKT",
                    "stopWhenRejected": true,
                    "frontendView": "hutang-piutang/creditlimit/view/mitra?",
                    "endpointOnFinished": "hutang-piutang/creditlimit/get",
                    "status": "WAITING FOR APPROVAL",
                    "level": [
                      {
                        "id": "0",
                        "level": "1",
                        "totalApprovalNeeded": "3",
                        "sendNotificationDate": "2024-11-28T07:31:27.118Z",
                        "approvalStatus": "WAITING FOR APPROVAL",
                        "approvalDate": "2024-11-28T07:31:27.118Z",
                        "approvalNote": "string",
                        "assignee": [
                          {
                            "id": "0",
                            "assigneeId": "1",
                            "assigneeName": "user",
                            "assigneeEmail": "user@gmail.com",
                            "assigneeWhatsapp": "0857xxxxxxxx",
                            "assigneeTelegram": "1",
                            "sendEmailNotification": true,
                            "sendWANotification": true,
                            "sendTelegramNotification": true,
                            "approvalStatus": "WAITING FOR APPROVAL",
                            "approvalDate": "2024-11-28T07:31:27.118Z",
                            "approvalNote": "string"
                          }
                        ]
                      }
                    ]
                  }
                ]
                """;

        System.out.println("start hit test client");
        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/approvalrequest/v1/0/update")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
//                    header.setBearerAuth(jwtAuthentication.getToken().getTokenValue());
                }).exchange().
                expectBody()
                .jsonPath("$.status").isEqualTo("Success");

        System.out.println("end hit test client");
    }


    @Test
    public void testDeleteApprovalRequest() throws Exception {

        String requestBody = """
                [
                  {
                    "id": "REQ/AUTONUMBER",
                    "deletedReason": "EVENT TIDAK JADI DIBUAT"
                  }
                ]
                """;

        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/approvalrequest/v1/0/delete")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
//                    header.setBearerAuth(jwtAuthentication.getToken().getTokenValue());
                }).exchange().
                expectBody()
                .jsonPath("$.status").isEqualTo("Success");

    }
}


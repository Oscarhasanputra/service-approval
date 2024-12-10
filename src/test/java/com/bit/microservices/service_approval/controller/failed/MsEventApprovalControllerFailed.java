package com.bit.microservices.service_approval.controller.failed;

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
public class MsEventApprovalControllerFailed extends AbstractConnectionContainer {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testCreateEventApprovalAlreadyExist() throws Exception {

        String requestBody = """
                [
                  {
                    "applicationId": "MC2~uuid",
                    "applicationCode": "MC2",
                    "code": "MYCOMPANY-CL",
                    "name": "REQUEST NAIK LIMIT MITRA",
                    "active": true,
                    "remarks": "string"
                  }
                ]
                """;


        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/eventapproval/v1/0/create")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
                }).exchange().
                expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo("Failed");

    }

    @Test
    public void testUpdateEventApprovalDoesntExist() throws Exception {

        String requestBody = """
                [
                  {
                    "applicationId": "MC~uuid",
                    "applicationCode": "MC",
                    "code": "MYCOMPANY-CL",
                    "name": "REQUEST NAIK LIMIT MITRA",
                    "active": true,
                    "remarks": "string",
                    "id": "MYCOMPANY-CL"
                  }
                ]
                """;


        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/eventapproval/v1/0/update")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
                }).exchange().
                expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo("Failed");

    }

    @Test
    public void testUpdateEventApprovalInconsistent() throws Exception {

        String requestBody = """
                [
                  {
                   "applicationId": "MC~uuid",
                    "applicationCode": "MC",
                    "code": "MYCOMPANY-CL2",
                    "name": "REQUEST NAIK LIMIT MITRA",
                    "active": true,
                    "remarks": "string",
                    "id": "MYCOMPANY-CL~a68038a0-97f2-4cd8-a836-52273604da94"
                  }
                ]
                """;


        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/eventapproval/v1/0/update")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
                }).exchange().
                expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo("Failed");

    }


    @Test
    public void testUpdateEventApprovalAlreadyDeleted() throws Exception {

        String requestBody = """
                [
                  {
                    "applicationId": "MC~uuid",
                    "applicationCode": "MC",
                    "code": "MYCOMPANY-CL2",
                    "name": "REQUEST NAIK LIMIT MITRA",
                    "active": true,
                    "remarks": "string",
                    "id": "MYCOMPANY-CL~a68038a0-97f2-4cd8-a836-52273604da95"
                  }
                ]
                """;


        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/eventapproval/v1/0/update")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
                }).exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo("Failed");

    }


    @Test
    public void testUpdateEventApprovalInvalid() throws Exception {

        String requestBody = """
                [
                  {
                   "applicationId": "MC~uuid",
                    "applicationCode": "MC",
                    "code": "MYCOMPANY-CL",
                    "name": "REQUEST NAIK LIMIT MITRA",
                    "active": true,
                    "remarks": "string",
                    "id": "0"
                  }
                ]
                """;


        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/eventapproval/v1/0/update")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
                }).exchange().
                expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo("Failed");

    }

    @Test
    public void testDeleteEventApprovalDoesntExist() throws Exception {

        String requestBody = """
                [
                  {
                    "id": "MYCOMPANY-CL~a68038a0-97f2-4cd8-a836",
                    "deletedReason": "EVENT TIDAK JADI DIBUAT"
                  }
                ]
                """;


        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/eventapproval/v1/0/delete")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
                }).exchange()
                .expectStatus().isNotFound().
                expectBody()
                .jsonPath("$.status").isEqualTo("Failed");

    }

}

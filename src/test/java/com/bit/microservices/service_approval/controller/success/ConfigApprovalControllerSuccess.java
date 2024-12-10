package com.bit.microservices.service_approval.controller.success;

import com.bit.microservices.service_approval.configuration.OauthProperties;
import com.bit.microservices.service_approval.controller.AbstractConnectionContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:bootstrap.yml")
@AutoConfigureWebTestClient
public class ConfigApprovalControllerSuccess extends AbstractConnectionContainer {

    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void testCreateConfigApproval() throws Exception {

        String requestBody = """
                [
                  {
                    "id": "COF/AUTONUMBER2",
                    "effectiveDate": "2024-11-28T08:55:03.198Z",
                    "eventApprovalId": "MYCOMPANY-CL~a68038a0-97f2-4cd8-a836-52273604da94",
                    "eventApprovalCode": "MYCOMPANY-CL",
                    "branchId": "JKT",
                    "branchCode": "JKT",
                    "stopWhenRejected": true,
                    "frontendView": "hutang-piutang/creditlimit/view/mitra?",
                    "endpointOnFinished": "hutang-piutang/creditlimit/get",
                    "autoSendRequest": true,
                    "remarks": "string",
                    "subevent": [
                      {
                        "id": "0",
                        "type": "SUBEVENT",
                        "code": "SMALL",
                        "autoApproved": false,
                        "remarks": "string",
                        "whitelistuser": [
                          {
                            "id": "0",
                            "whitelistuserType": "USER INTERNAL",
                            "whitelistuserId": "0",
                            "whitelistuserAttribute": "User A"
                          }
                        ],
                        "assignee": [
                          {
                            "id": "0",
                            "level": "1",
                            "totalApprovalNeeded": "3",
                            "assigneeType": "USER",
                            "assigneeId": "1",
                            "assigneeCode": "ATTRIBUTE"
                          }
                        ]
                      }
                    ]
                  }
                ]
                """;


        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/configapproval/v1/0/create")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
                }).exchange().
                expectBody()
                .jsonPath("$.status").isEqualTo("Success");

    }


    @Test
    public void testUpdateConfigApproval() throws Exception {

        String requestBody = """
                [
                  {
                    "id": "COF/AUTONUMBER",
                    "effectiveDate": "2024-11-28T08:55:03.198Z",
                    "eventApprovalId": "MYCOMPANY-CL~a68038a0-97f2-4cd8-a836-52273604da94",
                    "eventApprovalCode": "MYCOMPANY-CL",
                    "branchId": "JKT",
                    "branchCode": "JKT",
                    "stopWhenRejected": true,
                    "frontendView": "hutang-piutang/creditlimit/view/mitra?",
                    "endpointOnFinished": "hutang-piutang/creditlimit/get",
                    "autoSendRequest": true,
                    "remarks": "string",
                    "subevent": [
                      {
                        "id": "0",
                        "type": "SUBEVENT",
                        "code": "SMALL",
                        "autoApproved": false,
                        "remarks": "string",
                        "whitelistuser": [
                          {
                            "id": "0",
                            "whitelistuserType": "USER INTERNAL",
                            "whitelistuserId": "0",
                            "whitelistuserAttribute": "User A"
                          }
                        ],
                        "assignee": [
                          {
                            "id": "0",
                            "level": "1",
                            "totalApprovalNeeded": "3",
                            "assigneeType": "USER",
                            "assigneeId": "1",
                            "assigneeCode": "ATTRIBUTE"
                          }
                        ]
                      }
                    ]
                  }
                ]
                """;


        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/configapproval/v1/0/update")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
                }).exchange().
                expectStatus().isOk().
                expectBody()
                .jsonPath("$.status").isEqualTo("Success");

    }



    @Test
    public void testDeleteConfigApproval() throws Exception {

        String requestBody = """
                [
                  {
                    "id": "COF/AUTONUMBER",
                    "deletedReason": "EVENT TIDAK JADI DIBUAT"
                  }
                ]
                """;


        webTestClient
                .mutateWith(SecurityMockServerConfigurers.mockAuthentication(jwtAuthentication))
                .post().uri(BASE_URL+"/bit-service-approval/approval/configapproval/v1/0/delete")
                .bodyValue(requestBody).headers((header)->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.set(MOCK_TOKEN,MOCK_TOKEN_VALUE);
                }).exchange().
                expectBody()
                .jsonPath("$.status").isEqualTo("Success");

    }

}

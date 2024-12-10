package com.bit.microservices.service_approval.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractConnectionContainer {

    protected JwtAuthenticationToken jwtAuthentication;

    protected String BASE_URL = "";

    protected String MOCK_TOKEN = "tokenMock";
    protected String MOCK_TOKEN_VALUE = "userTester";


    @LocalServerPort
    private int port;

    @Container
    static PostgreSQLContainer<?> POSTGRES_CONTAINER= new PostgreSQLContainer<>("postgres:15").withUsername("postgres").withPassword("postgres");

    private static final String PATH_SQL_DATA_TEST = "src/test/resources/sql_data_testing.sql";

    private static final String PATH_SQL_STRUCTURE_TEST = "src/test/resources/sql_structure.sql";
    @DynamicPropertySource
    static void dynamicProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }

    @BeforeAll
    public static void setUp(){
        POSTGRES_CONTAINER.start();
        createDatabaseAndTable();
    }

    @BeforeEach
    public void setMock(){
        String tokenStatic=  "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJaUGxzc1VDYnFxZ2pGSDc1RkpGdmVGWUpKekk3MU1oaGJtNG4tb3pVWDRZIn0.eyJleHAiOjE3MzI3ODMzNDEsImlhdCI6MTczMjc3NjE0MSwianRpIjoiNzZkYWZmZWMtZmZjNC00MTliLTgxMWMtYTQzMWExN2EzOTkwIiwiaXNzIjoiaHR0cHM6Ly9sb2dpbi1zaXQuYmhha3RpLmNvLmlkOjg0NDMvcmVhbG1zL2JpdCIsInN1YiI6ImQ2NzdlYjQ2LTJkZmQtNDkzMi05MTBiLWEwZDY3NDU1MTIyMSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImIyYiIsInNpZCI6ImU3NGM4ODNlLWFkYzEtNGIwZC05MDg0LTZjZWJjMDQ2MWZlNCIsImFjciI6IjEiLCJyZXNvdXJjZV9hY2Nlc3MiOnsiYjJiIjp7InJvbGVzIjpbImIyYi11aS1sb2dpbi1hY2Nlc3MiLCJiMmIiLCJ1bWFfcHJvdGVjdGlvbiIsImJoYWt0aSJdfX0sInNjb3BlIjoiYjJiIGJoYWt0aSIsImNsaWVudEhvc3QiOiIxMC4zMC4xLjIyMCIsImFjdGl2ZUJyYW5jaCI6eyJpZCI6NywiY29kZSI6IkpLVCIsIm5hbWUiOiJKQUtBUlRBIiwiYnJhbmNoSGVhZElkIjoxOTQxfSwicm9sZUlkIjoyNiwiYnJhbmNoUmVnaW9uIjpbMiwzLDQsNSw2LDcsMTAsMTEsMTIsMTMsMTQsMTUsMTYsMTcsMTgsMTksMjAsMjEsMjIsMjMsMjQsMjUsMjYsMjcsMjgsMjksMzAsMzEsMzIsMzMsMzQsMzUsMzYsMzcsMzgsMzksNDAsNDEsNDIsNDMsNDQsNDUsNDYsNDcsNDgsNDksNTAsNTEsNTIsNTMsNTQsNTUsNTYsNTcsNTgsNTksNjAsNjEsNjIsNjMsNjQsNjUsNjYsNjcsNjgsNjksNzAsMTAxLDEwMywxMDQsMTA1XSwidXNlcklkIjoyNDgwLCJicmFuY2giOlsxLDIsMyw0LDUsNiw3LDgsOSwxMCwxMSwxMiwxMywxNCwxNSwxNiwxNywxOCwxOSwyMCw0MF0sImNsaWVudEFkZHJlc3MiOiIxMC4zMC4xLjIyMCIsImNsaWVudF9pZCI6ImIyYiIsInpvbmUiOiIrMDc6MDAiLCJuYW1lIjoiT1NDQVIgSEFTQU4gUFVUUkEiLCJhY3RpdmVCcmFuY2hSZWdpb24iOnsiaWQiOjExLCJjb2RlIjoiSkIiLCJuYW1lIjoiSkFNQkkiLCJicmFuY2hSZWdpb25IZWFkSWQiOjgxMH0sImlzRXh0ZXJuYWxPd25lciI6ZmFsc2UsInVzZXJFbWFpbCI6Im9zY2FyaGFzYW5wdXRyYTFAZ21haWwuY29tIiwidXNlclR5cGUiOiJJbnRlcm5hbCIsInRhZyI6W10sInNlc3Npb25fc3RhdGUiOiJlNzRjODgzZS1hZGMxLTRiMGQtOTA4NC02Y2ViYzA0NjFmZTQifQ.ZpZmXyirbI_j6Q0GuYSL6w3CocRbJrYEh5JB9rTu1nVxgF3xPKwEFW2G-3XGJ9kVac1EuK2c6gjeUNTppZYwMUkR3zGkEG9QQmVZ7L_kSTgRIHZek-VB9eIjIXBCjcUNqFQyYaSUyYJRJyJIcrQNC82Z92zBumu9dGM4xaGBgDFg9z7DXn4QxZl-IqS2ifO0iVAecZgv-d5VbSWjxYhvYUs95GG412emesFkgCog7NE4ahPAbFcLiKp6ekdV1XUsMzAWL5j90mQEWgemJPScS-7p8ZkczNYcVrs5lUJpQ5QaKts4ZOSXcYsENWAB2HXR53A5bnWKTNQ9LaWaGDxaCw";

        Jwt jwt = Jwt.withTokenValue(tokenStatic)
                .header("alg","none")
                .claim("userEmail","userTest@gmail.com")
                .claim("userId","01")
                .claim("scope","bhakti")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("SCOPE_bhakti"));

        // Create a JwtAuthenticationToken with the authorities
        jwtAuthentication = new JwtAuthenticationToken(jwt, authorities);
        BASE_URL="http://localhost:"+port;
    }

    @AfterAll
    public static void tearDown() {
       POSTGRES_CONTAINER.stop();
    }

    private static void createDatabaseAndTable(){
        try (Connection connection = POSTGRES_CONTAINER.createConnection("")) {
            // Create a new database if necessary
            connection.createStatement().execute("CREATE DATABASE BIT_SERVICE_APPROVAL_SIT_TEST;");

            String sqlTestData = Files.readString(Path.of(PATH_SQL_DATA_TEST));


            // Read the SQL file
            String sqlStructure = Files.readString(Path.of(PATH_SQL_STRUCTURE_TEST));
//
            // Execute the SQL commands

            connection.createStatement().execute(sqlStructure);
            connection.createStatement().execute(sqlTestData);
        System.out.println("dummy data");
            System.out.println(sqlStructure);
            System.out.println(sqlTestData);
        } catch (SQLException | IOException e) {
            System.out.println("connection error ");
            System.out.println(e);
        }
    }
}


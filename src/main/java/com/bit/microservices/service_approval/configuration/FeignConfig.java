package com.bit.microservices.service_approval.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Configuration
@Slf4j
public class FeignConfig {

	@Autowired
    private ObjectMapper mapper;

	private String appVersion = System.getenv("APP_VERSION");

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_TYPE = "Bearer";

	@Bean
	public PageJacksonModule pageJacksonModule() {
	     return new PageJacksonModule();
	}
	
	@Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
	
	@Bean
	@Primary
	public RequestInterceptor requestInterceptor() {

		return requestTemplate -> {

			if (SecurityContextHolder.getContext().getAuthentication() != null) {
	            
	            if (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken) {
	                
	                JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
	                Jwt jwt = (Jwt) authentication.getPrincipal();
	                
	                requestTemplate.header(AUTHORIZATION_HEADER,
	        		String.format("%s %s", TOKEN_TYPE, jwt.getTokenValue()));
	                
	            }
			}

			requestTemplate.header("Content-Type", "application/json");
				requestTemplate.header("Accept", "application/json");

				StringTokenizer appVersionTokenizer = new StringTokenizer(appVersion, ".");
			        
				List<String> app_versions = new ArrayList<>();
				while (appVersionTokenizer.hasMoreElements()) {
					app_versions.add(appVersionTokenizer.nextToken());
				}

				requestTemplate.header("X-API-VERSION", String.join(".", app_versions.get(2), app_versions.get(3)));
		};
	}
}

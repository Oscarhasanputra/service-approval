package com.bit.microservices.service_approval.configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Slf4j
@Configuration
public class OpenApiConfig {

	private String appVersion = System.getenv("APP_VERSION");

	private final String packageToScanStr = "com.bit.microservices.service_approval";

	private final String exceptionToScan = "com.bit.microservices.service_approval.exception";

	private final String modelToScan = "com.bit.microservices.service_approval.model";


	private final String eventApprovalApi = "/**/eventapproval/**";

	private final String configApprovalApi = "/**/configapproval/**";

	private final String approvalHistoryApi="/**/approvalrequest/**";
	@Bean
	public GroupedOpenApi eventApprovalApi() {
		String packagesToscan[] = { packageToScanStr , exceptionToScan, modelToScan };//, bcaExceptionToScan, va2ExceptionToScan, modelToScan };
		String pathsExclude[] = {};
		String pathsInclude[] = { eventApprovalApi };
		return GroupedOpenApi.builder()
				.group("1. Event Approval")
				.packagesToScan(packagesToscan)
				.pathsToMatch(pathsInclude)
				.pathsToExclude(pathsExclude)
				.addOpenApiCustomizer(internalApiCustomizer())
				.build();
	}

	@Bean
	public GroupedOpenApi configApprovalApi() {
		String packagesToscan[] = { packageToScanStr , exceptionToScan, modelToScan };//, bcaExceptionToScan, va2ExceptionToScan, modelToScan };
		String pathsExclude[] = {};
		String pathsInclude[] = { configApprovalApi };
		return GroupedOpenApi.builder()
				.group("2. Config Approval")
				.packagesToScan(packagesToscan)
				.pathsToMatch(pathsInclude)
				.pathsToExclude(pathsExclude)
				.addOpenApiCustomizer(internalApiCustomizer())
				.build();
	}

	@Bean
	public GroupedOpenApi historyApprovalApi() {
		String packagesToscan[] = { packageToScanStr , exceptionToScan, modelToScan };//, bcaExceptionToScan, va2ExceptionToScan, modelToScan };
		String pathsExclude[] = {};
		String pathsInclude[] = { approvalHistoryApi };
		return GroupedOpenApi.builder()
				.group("3. Approval History")
				.packagesToScan(packagesToscan)
				.pathsToMatch(pathsInclude)
				.pathsToExclude(pathsExclude)
				.addOpenApiCustomizer(internalApiCustomizer())
				.build();

	}
	@Bean
	public OpenApiCustomizer internalApiCustomizer() {

		return openApi -> {
			openApi.addSecurityItem(new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")));
			Info info = openApi.getInfo();
			info.setVersion(appVersion);
			openApi.setInfo(info);
			// openApi.components(new Components());
			openApi.getComponents().addSecuritySchemes("bearer-jwt",
					new SecurityScheme()
							.type(SecurityScheme.Type.HTTP)
							.scheme("bearer")
							.bearerFormat("JWT")
							.in(SecurityScheme.In.HEADER)
							.name("Authorization"));
		};
	}
}

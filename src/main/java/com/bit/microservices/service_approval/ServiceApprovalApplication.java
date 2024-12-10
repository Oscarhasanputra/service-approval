package com.bit.microservices.service_approval;

import com.bit.microservices.configuration.BaseEventListener;
import com.bit.microservices.configuration.CustomKeyGenerator;
import com.bit.microservices.configuration.FeignErrorDecoder;
import com.bit.microservices.redis.impl.RedisAuthenticationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@Import({
		LoadBalancerAutoConfiguration.class,
		BaseEventListener.class,
		FeignErrorDecoder.class,
		CustomKeyGenerator.class,
		RedisAuthenticationRepository.class
})
@SpringBootApplication(exclude={ReactiveSecurityAutoConfiguration.class})
@EnableJpaRepositories(
		basePackages= {"com.bit.microservices.service_approval.entity", "com.bit.microservices.service_approval.repository"},
		repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableWebFlux
@Slf4j
@RefreshScope
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages= {"com.bit.microservices.service_approval"})
public class ServiceApprovalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApprovalApplication.class, args);
	}

}

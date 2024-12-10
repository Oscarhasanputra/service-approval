package com.bit.microservices.service_approval.configuration;

import io.micrometer.observation.ObservationRegistry;
import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationRegistryCustomizer;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.observation.ServerRequestObservationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.reactive.config.PathMatchConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.server.i18n.FixedLocaleContextResolver;
import org.springframework.web.server.i18n.LocaleContextResolver;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import javax.net.ssl.SSLException;
import java.time.Duration;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class WebConfig implements WebFluxConfigurer{

	private static final Long MAX_AGE = 3600l;

	
	@Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
			configurer.defaultCodecs().maxInMemorySize(-1);
			configurer.defaultCodecs().enableLoggingRequestDetails(true);
    }
	
	@Override
    public void configurePathMatching(PathMatchConfigurer configurer) {
		configurer.addPathPrefix("/bit-service-approval", c -> true);
	}
	
	@Bean
    NettyServerCustomizer nettyServerCustomizer() {
        return httpServer -> httpServer.idleTimeout(Duration.ofSeconds(300)).wiretap(true)
        		.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 360000)
        		.option(ChannelOption.AUTO_CLOSE, true)
        		.option(ChannelOption.TCP_NODELAY, true);
    }
	
	@Bean("httpClient")
    @Primary
    HttpClient httpClient(){
		return HttpClient.create(
				ConnectionProvider.builder("aws-connection-provider").maxIdleTime(Duration.ofSeconds(300)).build())
				.resolver(DefaultAddressResolverGroup.INSTANCE)
				.wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 360000)
				.option(ChannelOption.TCP_NODELAY, true)
				.responseTimeout(Duration.ofSeconds(60))
				.doOnConnected(conn -> 
				    conn.addHandlerLast(new ReadTimeoutHandler(60, TimeUnit.SECONDS))
				      .addHandlerLast(new WriteTimeoutHandler(60, TimeUnit.SECONDS)));
	}

	@Bean("secureHttpClient")
    HttpClient httpsClient() throws SSLException{
		
		SslContext sslContext = SslContextBuilder
    		      .forClient()
    		      .trustManager(InsecureTrustManagerFactory.INSTANCE)
    		      .build();
    			
		return HttpClient.create(
				ConnectionProvider.builder("aws-connection-provider").maxIdleTime(Duration.ofSeconds(300)).build())
				.resolver(DefaultAddressResolverGroup.INSTANCE)
				.secure(sslContextSpec -> sslContextSpec.sslContext(sslContext))
				.wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 360000)
				.option(ChannelOption.TCP_NODELAY, true)
				.responseTimeout(Duration.ofSeconds(60))
				.doOnConnected(conn -> 
				    conn.addHandlerLast(new ReadTimeoutHandler(60, TimeUnit.SECONDS))
				      .addHandlerLast(new WriteTimeoutHandler(60, TimeUnit.SECONDS)));
	}
	
	@Bean("webClient")
    @Primary
    WebClient.Builder webClientBuilder() throws Exception {
    	
        return WebClient.builder()
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.clientConnector(new ReactorClientHttpConnector(httpClient()))
					.codecs(configurer -> configurer.defaultCodecs()
						.maxInMemorySize(-1));
        
    }
    
    @Bean("secureWebClient")
    WebClient.Builder secureWebClientBuilder() throws Exception {
    	
        return WebClient.builder()
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.clientConnector(new ReactorClientHttpConnector(httpsClient()))
					.codecs(configurer -> configurer.defaultCodecs()
						.maxInMemorySize(-1));
    }
    
    @Bean("lbClient")
    @LoadBalanced
    WebClient.Builder lbClient() throws Exception {
    	
        return WebClient.builder()
					.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.clientConnector(new ReactorClientHttpConnector(httpClient()))
					.codecs(configurer -> configurer.defaultCodecs()
						.maxInMemorySize(-1));
    }

    @Bean
    ObservationRegistryCustomizer<ObservationRegistry> skipActuatorEndpointsFromObservation() {
        PathMatcher pathMatcher = new AntPathMatcher("/");
        return (registry) -> registry.observationConfig().observationPredicate((name, context) -> {
            if (context instanceof ServerRequestObservationContext observationContext) {
                return !pathMatcher.match("/actuator/**", observationContext.getCarrier().getPath().value()) ||
                		!pathMatcher.match("/*/actuator/**", observationContext.getCarrier().getPath().value());
            } else {
                return true;
            }
        });
    }
    
	@Bean
	ObservationRegistryCustomizer<ObservationRegistry> skipOptionsFromObservation() {
	      return (registry) -> registry.observationConfig().observationPredicate((name, context) -> {
	          if (context instanceof ServerRequestObservationContext observationContext) {
	              return !HttpMethod.OPTIONS.matches(observationContext.getCarrier().getMethod().name());
	          } else {
	              return name.indexOf("options") == -1;
	//              return true;
	          }
	      });
	}
	
	@Bean
	@Primary
    LocaleContextResolver localeContextResolver(){
        return new FixedLocaleContextResolver(Locale.getDefault(), TimeZone.getDefault());
    }
	
	@Bean
    HttpHandler httpHandler(ApplicationContext context){
        return WebHttpHandlerBuilder.applicationContext(context)
                .localeContextResolver(localeContextResolver())
                .build();

    }
	
}
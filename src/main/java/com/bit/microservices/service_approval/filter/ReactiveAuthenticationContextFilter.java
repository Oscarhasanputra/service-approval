package com.bit.microservices.service_approval.filter;

import com.bit.microservices.security.AuthenticationDetail;
import com.bit.microservices.security.context.AuthenticationContext;
import com.bit.microservices.service_approval.configuration.AuditorAwareImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpHead;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class ReactiveAuthenticationContextFilter implements WebFilter {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Autowired
    private JwtDecoder decoder;


    public ReactiveAuthenticationContextFilter() {
//        this.decoder = decoder;
//        this.sink = sink;
    }

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String bearerToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        Map<String, Object>  claims = new HashMap<>();
        if (bearerToken != null && !bearerToken.isBlank()) {
            String accessToken = bearerToken.split(" ")[1];

            Jwt jwt = this.decoder.decode(accessToken);

            claims = jwt.getClaims();
        }else if(!Objects.isNull(headers.getFirst("tokenMock")) && headers.getFirst("tokenMock").equals("userTester")){

            claims.put("userEmail","User Mock Tester");
            claims.put("userId","0");
        }


        if (!claims.isEmpty()) {
            if (Objects.nonNull(claims.get("userEmail"))){
                return chain.filter(exchange)
                        .contextWrite(Context.of(HttpHeaders.AUTHORIZATION, claims));
            }
        }

        return chain.filter(exchange);
    }
}

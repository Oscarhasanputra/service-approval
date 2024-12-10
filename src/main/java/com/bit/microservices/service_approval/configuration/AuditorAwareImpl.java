package com.bit.microservices.service_approval.configuration;

import com.bit.microservices.security.AuthenticationDetail;
import com.bit.microservices.service_approval.filter.UserContextData;
import com.bit.microservices.service_approval.filter.UserData;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpHead;
import org.htmlparser.http.HttpHeader;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import javax.naming.Context;
import java.util.Optional;
import java.util.concurrent.Executor;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {

        UserData userData = UserContextData.getUserData();

        String email = userData.getEmail();
        String userId = userData.getUserId();
        UserContextData.removeData();
    	return Optional.of(email+"("+userId+")");
    }

}

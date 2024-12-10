package com.bit.microservices.service_approval.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ReactiveSecurityContextHolderData {

    public static Mono<Map<String,Object>> assignContextData(){
        System.out.println("context assign nich 2");
        return Mono.deferContextual(Mono::just).map((ctx)->{
            System.out.println("context assign nich ");
            Map<String,Object> claim = (Map) ctx.get(HttpHeaders.AUTHORIZATION);
            UserData userData = new UserData();
            userData.setEmail(claim.get("userEmail").toString());
            userData.setUserId(claim.get("userId").toString());

            UserContextData.setUserData(userData);
            return claim;
        });
    }
}

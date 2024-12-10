package com.bit.microservices.service_approval.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.io.Serializable;

@ConfigurationProperties("b2b")
@Data
@RefreshScope
public class B2BProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productSpecificId;
    
    private String partnerSpecificId;
    
    private String partnerShipmentSpecificId;
    
}

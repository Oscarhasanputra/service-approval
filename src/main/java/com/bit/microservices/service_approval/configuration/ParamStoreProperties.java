package com.bit.microservices.service_approval.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@ConfigurationProperties("cloud.aws.paramstore")
@Data
@RefreshScope
public class ParamStoreProperties{

    private String bitVaClientIdName;
    private String bitVaClientSecretName;
    private String bitVaPrivateKeyName;
    private String bitVaTransferStatusUrl;
    private String bitVaAccessTokenUrl;
    private String bitVaBcaBaseUrl;
    private String bcaClientSecretName;
}

package com.bit.microservices.service_approval.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RequestDetailDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -627462843101852844L;

    private String id;
}

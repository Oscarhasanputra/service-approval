package com.bit.microservices.service_approval.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Data
public class TestService {

    private int counter = 0;

}

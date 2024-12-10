package com.bit.microservices.service_approval.configuration.graphql;

import graphql.scalars.ExtendedScalars;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;
@Slf4j
@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurerLong() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.GraphQLLong);
    }


    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurerDateTime() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.DateTime);
    }
}

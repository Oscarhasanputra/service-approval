package com.bit.microservices.service_approval.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableConfigurationProperties({OauthProperties.class, RedisProperties.class, ParamStoreProperties.class, B2BProperties.class})
public class PropertySourcesConfig extends DefaultPropertySourceFactory{
	@Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if (resource == null){
            return super.createPropertySource(name, resource);
        }
        List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
        for (PropertySource<?> checkSource : sources) {
            if (checkSource.containsProperty("spring.profiles.active")) {
                String activeProfile = (String) checkSource.getProperty("spring.profiles.active");
                for (PropertySource<?> source : sources) {
                    if (activeProfile.trim().equals(source.getProperty("spring.profiles"))) {
                        return source;
                    }
                }
            }
        }
        return sources.get(0);
    }
}

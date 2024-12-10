package com.bit.microservices.service_approval.configuration;

import com.bit.microservices.utils.OffsetDateTimeDeserializer;
import com.bit.microservices.utils.OffsetDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SortJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

	@Bean
	@Primary
	ObjectMapper objectMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		
		mapper.writerWithDefaultPrettyPrinter();
		mapper.findAndRegisterModules();
		
		mapper.registerModule(pageJacksonModule());
        mapper.registerModule(sortJacksonModule());
        mapper.registerModule(jdk8JacksonModule());
        mapper.registerModule(javaTimeJacksonModule());
		
		mapper.setSerializationInclusion(Include.ALWAYS);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //harus ada ini biar bisa Pageable di feign

		return mapper;
	}


	@Bean
	@Primary
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
	    return new ModelResolver(objectMapper);
	}
	
	@Bean
    public PageJacksonModule pageJacksonModule() {
         return new PageJacksonModule();
    }
	
	@Bean
    public SortJacksonModule sortJacksonModule() {
         return new SortJacksonModule();
    }
	
	@Bean
    public Jdk8Module jdk8JacksonModule() {
         return new Jdk8Module();
    }
	
	@Bean
    public JavaTimeModule javaTimeJacksonModule() {
		JavaTimeModule module = new JavaTimeModule();
		LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
		
		LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		module.addSerializer(LocalDateTime.class, localDateTimeSerializer);
		
		module.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
		module.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer());
		
		return module;
    }
}

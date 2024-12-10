package com.bit.microservices.service_approval.util;


import org.hibernate.SessionFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class HibernateConfiguration {
//    private static final GenericContainer<?> redis;
//
////    @Container
//    static PostgreSQLContainer<?> POSTGRES_CONTAINER;
//    static {
//        System.out.println("on setup nich test");
//
//
//        redis =new GenericContainer<>("redis:6.2.5")
//                .withExposedPorts(6379);
//        redis.start();
//
//        String redisHost = redis.getHost();
//        Integer redisPort = redis.getMappedPort(6379);
//
//        System.setProperty("spring.redis.host", redisHost);
//        System.setProperty("spring.redis.port", redisPort.toString());
//
//
//        POSTGRES_CONTAINER= new PostgreSQLContainer<>("postgres:15")
//                .withDatabaseName("testdb")
//                .withUsername("test")
//                .withPassword("test");
//
//        POSTGRES_CONTAINER.start();
//    }
////
////
////
//    @Bean
//    SessionFactory sessionFactory(DataSource dataSources,RedissonClient redissonClient) throws IOException {
//
////        Properties properties = new Properties();
////        properties.put(Environment.DRIVER, "org.postgresql.Driver");
////        properties.put(Environment.URL, POSTGRES_CONTAINER.getJdbcUrl());          // Use Testcontainer JDBC URL
////        properties.put(Environment.USER, POSTGRES_CONTAINER.getUsername());       // Use Testcontainer username
////        properties.put(Environment.PASS,POSTGRES_CONTAINER.getPassword());       // Use Testcontainer password
////        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
////        properties.put(Environment.HBM2DDL_AUTO, "update"); // Automatically create/update schema
////        properties.put(Environment.SHOW_SQL, "true");
////
////
////        org.hibernate.cfg.Configuration configuration = new Configuration();
////        configuration.setProperties(properties);
////        return configuration.buildSessionFactory();
//
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSources);
//        System.out.println("session factory scan start tester");
//        sessionFactoryBean.setPackagesToScan("com.bit.microservices.service_approval.entity"); // package containing annotated entities
//        System.out.println("session factory scan end tester");
//        sessionFactoryBean.setHibernateProperties(hibernateProperties());
//
//        sessionFactoryBean.afterPropertiesSet();
//
//        System.out.println(sessionFactoryBean.getObject());
////        MsEventApproval
//        return sessionFactoryBean.getObject();
////        return Mockito.mock(SessionFactory.class,sessionFactoryBean.getObject());
//    }
//
//
//    @Bean
//    @Primary
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        // Configure Redisson for Redis (adjust host, port, etc. as needed)
//        config.useSingleServer().setAddress("redis://"+redis.getHost()+":"+redis.getMappedPort(6379));
//        System.out.println("config start redss");
//        return Redisson.create(config);
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory);
//        return transactionManager;
//    }
//
//    @Bean
//    @Primary
//    public DataSource dataSource() {
//        // Make sure the DataSource is correctly configured, especially for the TestContainer
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl(POSTGRES_CONTAINER.getJdbcUrl());
//        dataSource.setUsername(POSTGRES_CONTAINER.getUsername());
//        dataSource.setPassword(POSTGRES_CONTAINER.getPassword());
//        return dataSource;
//    }
//
////    @Bean
////    public PlatformTransactionManager hibernateTransactionManager(LocalSessionFactoryBean sessionFactory) {
////        System.out.println("hibernate transaction manager");
////        System.out.println(sessionFactory);
////        System.out.println(sessionFactory.getObject());
////        return new HibernateTransactionManager(sessionFactory.getObject());
////    }
//
//    private Properties hibernateProperties() {
//        Properties properties = new Properties();
//
//        Integer redisPort = redis.getMappedPort(6379);
//        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        properties.put("hibernate.hbm2ddl.auto", "create-drop");
//        properties.put("hibernate.format_sql","true");
//        properties.put("hibernate.show_sql","true");
//        properties.put("hibernate.cache.region.factory_class", "org.redisson.hibernate.RedissonRegionFactory");
//        properties.put("hibernate.cache.use_second_level_cache", "false");
//        properties.put("hibernate.cache.use_query_cache", "false");
//
////        properties.put("hibernate.integrator_provider", "io.hypersistence.utils.hibernate.type.HibernateTypeContributor");
////        properties.put("hibernate.type_contributors", "io.hypersistence.utils.hibernate.type.HibernateTypeContributor");
//        return properties;
//    }
//



}

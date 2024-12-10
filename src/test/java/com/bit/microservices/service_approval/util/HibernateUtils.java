package com.bit.microservices.service_approval.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Properties;

public class HibernateUtils {

    static PostgreSQLContainer<?> POSTGRES_CONTAINER;
    static {
        System.out.println("on setup nich test");
        POSTGRES_CONTAINER= new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");

        POSTGRES_CONTAINER.start();
    }


    public static SessionFactory getSessionFactory(){

        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "org.postgresql.Driver");
        properties.put(Environment.URL, POSTGRES_CONTAINER.getJdbcUrl());          // Use Testcontainer JDBC URL
        properties.put(Environment.USER, POSTGRES_CONTAINER.getUsername());       // Use Testcontainer username
        properties.put(Environment.PASS,POSTGRES_CONTAINER.getPassword());       // Use Testcontainer password
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.HBM2DDL_AUTO, "update"); // Automatically create/update schema
        properties.put(Environment.SHOW_SQL, "true");


        org.hibernate.cfg.Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        return configuration.buildSessionFactory();
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSources);
//        sessionFactoryBean.setPackagesToScan("com.example.yourpackage"); // package containing annotated entities
//        sessionFactoryBean.setHibernateProperties(hibernateProperties());
//        System.out.println("properties get nich");
//
//        System.out.println(sessionFactoryBean);
//        return sessionFactoryBean;
//        return Mockito.mock(SessionFactory.class,sessionFactoryBean.getObject());
    }

}

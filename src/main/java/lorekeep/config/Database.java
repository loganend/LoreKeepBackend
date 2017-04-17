//package lorekeep.config;//package com.fifabot.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories("lorekeep")
//public class Database {
//
//    @Value("${database.url}")
//    private String serverName;
//    @Value("${database.username}")
//    private String user;
//    @Value("${database.password}")
//    private String password;
//    @Value("${database.name}")
//    private String name;
//    @Value("${database.pool}")
//    private int pool;
//    @Value("${database.showSql}")
//    private String showSql;
//
//    @Bean(destroyMethod = "close")
//    public HikariDataSource dataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
//        dataSource.addDataSourceProperty("serverName", serverName);
//        dataSource.addDataSourceProperty("user", user);
//        dataSource.addDataSourceProperty("password", password);
//        dataSource.addDataSourceProperty("databaseName", name);
//        dataSource.setConnectionTimeout(500);
//        dataSource.setMaximumPoolSize(pool);
//        return dataSource;
//    }
//
//
//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource());
//        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
//        entityManagerFactoryBean.setPackagesToScan("com.fifabot");
//
//        Properties jpaProperties = new Properties();
//        jpaProperties.put("hibernate.cache.use_second_level_cache", "false");
//        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
//        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
//        jpaProperties.put("hibernate.connection.charSet", "utf8");
//        jpaProperties.put("hibernate.connection.characterEncoding", "utf8");
//        jpaProperties.put("hibernate.connection.useUnicode", "true");
//        jpaProperties.put("hibernate.show_sql", showSql);
//        jpaProperties.put("hibernate.jdbc.batch_size", 100);
//        jpaProperties.put("hibernate.order_inserts", true);
//        jpaProperties.put("hibernate.order_updates", true);
//
//        jpaProperties.put("jadira.usertype.autoRegisterUserTypes", "true");
//        jpaProperties.put("jadira.usertype.databaseZone", "jvm");
//        jpaProperties.put("jadira.usertype.javaZone", "jvm");
//
//        entityManagerFactoryBean.setJpaProperties(jpaProperties);
////        entityManagerFactoryBean.afterPropertiesSet();
//
//        return entityManagerFactoryBean.getObject();
//    }
//
//    @Bean
//    public JpaTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory());
//        transactionManager.setDataSource(dataSource());
//        transactionManager.afterPropertiesSet();
//        return transactionManager;
//    }
//
//}

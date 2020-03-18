package com.darcy.nacos.core.catalog;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.darcy.nacos.core.model.Users;
import com.darcy.nacos.core.base.EnhancedJpaRepository;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "catalogEntityManagerFactory",
        transactionManagerRef = CatalogRepositoryConfig.TX_MANAGER_NAME,
        repositoryBaseClass = EnhancedJpaRepository.class
)
@EnableTransactionManagement
@ComponentScan
public class CatalogRepositoryConfig {

    private static final String UNIT_NAME = "catalog";

    static final String TX_MANAGER_NAME = "catalogTransactionManager";

    @Bean
    @ConfigurationProperties(prefix = "catalog.datasource")
    @Primary
    @Qualifier(UNIT_NAME)
    public DataSource catalogDataSource() {
        return new HikariDataSource();
    }

    @Bean
    @Primary
    @Qualifier(UNIT_NAME)
    public PlatformTransactionManager catalogTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setPersistenceUnitName(UNIT_NAME);
        return transactionManager;
    }

    @Bean
    @Primary
    @Qualifier(UNIT_NAME)
    public LocalContainerEntityManagerFactoryBean catalogEntityManagerFactory(@Qualifier(UNIT_NAME) DataSource catalogDataSource,
                                                                              EntityManagerFactoryBuilder builder, JpaProperties jpaProperties, HibernateProperties hibernateProperties) {
        return builder.dataSource(catalogDataSource)
                .packages(Users.class, Jsr310JpaConverters.class)
                .persistenceUnit(UNIT_NAME)
                .properties(hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings()))
                .build();
    }

}


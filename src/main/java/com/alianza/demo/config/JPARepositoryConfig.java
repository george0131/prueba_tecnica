package com.alianza.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.alianza.demo.persistence.repository",
        repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
public class JPARepositoryConfig {
}

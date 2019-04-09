package com.jpop.productservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.jpop.productservice.dao")
public class ApplicationConfiguration {

}

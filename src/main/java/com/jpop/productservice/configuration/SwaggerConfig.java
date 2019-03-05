package com.jpop.productservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(ApiIgnore.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jpop.productservice.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiinfo());
    }

    private ApiInfo apiinfo() {
        return new ApiInfo(
                "Product Service API",
                "Product service API to perform CRUD operations.",
                "V1",
                null,
                null,
                "License of API", "API license URL", Collections.emptyList());
    }
}
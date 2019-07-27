package com.imdb.titles.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
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
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.imdb.titles.rest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "IMDB Titles Service",
                "This API returns titles and their related information that were created in 2018. It also" + "returns a single title and allows for ratings to be updated at random",
                "Netflix",
                "https://www.imdb.com/conditions?ref_=helpms_ih_gi_usedata",
                new Contact("Max Moise", "www.example.com", "max.moise@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}


package com.puigros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.*;

import static springfox.documentation.schema.AlternateTypeRules.newRule;
import com.fasterxml.classmate.TypeResolver;
/**
 * The main application class
 * @author crequena
 */
@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = { "com.puigros" })
@EnableAutoConfiguration(exclude = { AopAutoConfiguration.class })
@EnableSwagger2
@SuppressWarnings("squid:S1118")
public class MainApplication {
    private static Class<MainApplication> mainApplicationClass = MainApplication.class;
    @Autowired
    private TypeResolver typeResolver;
    /**
     * The main method.
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(mainApplicationClass, args); //NOSONAR
    }
    @Bean
    public Docket docketFactory() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metadata())
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(OffsetDateTime.class, String.class)
                .directModelSubstitute(OffsetTime.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .select()
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * Metadata.
     *
     * @return the api info
     */
    @Bean
    public ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("Spring boot Kafka Producer")
                .description(
                        "Spring boot Kafka Producer "
                )
                .version("1.0")
                .build();
    }
}

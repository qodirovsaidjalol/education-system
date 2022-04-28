package com.education;

import com.education.properties.OpenApiProperties;
import com.education.properties.ServerProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableConfigurationProperties({
        OpenApiProperties.class,
        ServerProperties.class
})
@OpenAPIDefinition
@RequiredArgsConstructor
@Log4j2
@SpringBootApplication
public class EducationSystemApplication {

    public static void main(String[] args) {

        SpringApplication.run(EducationSystemApplication.class, args);
    }
}

package com.hzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.oas.annotations.EnableOpenApi;

@MapperScan
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableOpenApi
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}

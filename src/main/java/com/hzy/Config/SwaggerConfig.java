package com.hzy.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Auther: hzy
 * @Date: 2021/10/22 19:17
 * @Description:
 */


@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("(?!/error.*).*"))
                .build()
                .apiInfo(
                new ApiInfoBuilder()
                        .contact(new Contact("hzy",
                                "https://docs.apipost.cn/preview/9509bea7b0e106f8/a164bc711c06035f",
                                "422511186@qq.com"))
                        .title("Modeshape内容存储库")
                        .version("1.0")
                        .build()
        );
    }
}

package com.hzy.Config;

import com.google.gson.Gson;
import com.hzy.Factory.ModeShapeRepositoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration
public class AdminWebConfig  implements WebMvcConfigurer {


    private  static final String CONFIG_DEV = "my-repository-config-dev.json";
    private  static final String CONFIG_PROD = "my-repository-config-prod.json";

    private static  final  String PDFPATH_DEV = "D:/Desktop/css/";
    private static  final  String PDFPATH_PROD = "/home/ubuntu/MS/pdfs/";

    @Bean
    ModeShapeRepositoryFactory repositoryFactory() {
        ModeShapeRepositoryFactory factory = new ModeShapeRepositoryFactory();
        factory.setConfiguration(new ClassPathResource(CONFIG_PROD));
        return factory;
    }

    @Bean
    Gson gson(){
        return new Gson();
    }

    /**
     * 允许跨域调用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        log.info("允许跨域调用的过滤器");

        CorsConfiguration config = new CorsConfiguration();
        //允许所有域名进行跨域调用
        config.addAllowedOrigin("*");
        //允许跨越发送cookie
        config.setAllowCredentials(true);
        //放行全部原始头信息
        config.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * 跨域配置
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("跨域配置");

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:" + PDFPATH_PROD);
    }

}
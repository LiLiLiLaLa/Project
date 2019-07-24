package edu.xpu.game.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 跨域拦截器
 */
@Configuration
@EnableConfigurationProperties(DefinedWebMvcConfigurer.class)
@ConfigurationProperties(prefix = "tim.webmvc")
public class DefinedWebMvcConfigurer implements WebMvcConfigurer {
    /**
     * 跨域允许地址
     */
    private String[] corssOrigins = {"*"};

    /**
     * 跨域匹配地址
     */
    private String corssMapping = "/**";

    /**
     * 全局跨域设置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(corssMapping)
                .allowedOrigins(corssOrigins)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public String[] getCorssOrigins() {
        return corssOrigins;
    }

    public void setCorssOrigins(String[] corssOrigins) {
        this.corssOrigins = corssOrigins;
    }

    public String getCorssMapping() {
        return corssMapping;
    }

    public void setCorssMapping(String corssMapping) {
        this.corssMapping = corssMapping;
    }
}
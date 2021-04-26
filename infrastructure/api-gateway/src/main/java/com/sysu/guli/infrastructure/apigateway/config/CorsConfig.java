package com.sysu.guli.infrastructure.apigateway.config;

import com.sysu.guli.infrastructure.apigateway.handler.GlobalJsonExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.stream.Collectors;

@Configuration
public class CorsConfig {
    //网关配置类 将网关的配置filter加载到容器里
    //统一解决跨域问题
    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source  = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //允许携带cookie访问 setAllowCredentials 允许凭证
        config.addAllowedMethod("*");
        config.addAllowedHeader("*"); //请求头不限制
        config.addAllowedOrigin("*"); //对客户端地址不限制
        //"/**"是任意多级  config是跨域规则
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
    @Bean
    @Order(-1) //优先级必须为-1
    public GlobalJsonExceptionHandler jsonExceptionHandler(ErrorAttributes errorAttributes,
                                                           org.springframework.boot.autoconfigure.web.ResourceProperties resourceProperties,
                                                           ServerProperties serverProperties,
                                                           ObjectProvider<ViewResolver> viewResolvers,
                                                           ServerCodecConfigurer serverCodecConfigurer, ApplicationContext applicationContext){

        GlobalJsonExceptionHandler handler = new GlobalJsonExceptionHandler(errorAttributes, resourceProperties,
                serverProperties.getError(), applicationContext);
        handler.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()));
        handler.setMessageWriters(serverCodecConfigurer.getWriters());
        handler.setMessageReaders(serverCodecConfigurer.getReaders());
        return handler;
    }

}

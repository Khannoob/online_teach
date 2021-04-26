package com.sysu.guli.infrastructure.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
//当前项目无需使用 common模块下的依赖
public class InfrastructureApiGatewayApplication {
    public static void main(String[] args) {
    SpringApplication.run(InfrastructureApiGatewayApplication.class, args);

    }
}

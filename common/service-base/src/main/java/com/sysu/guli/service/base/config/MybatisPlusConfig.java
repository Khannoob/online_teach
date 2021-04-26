package com.sysu.guli.service.base.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.sysu.guli.service.*.mapper")
public class MybatisPlusConfig {

    //乐观锁
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

    @Bean
    //分页插件
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}

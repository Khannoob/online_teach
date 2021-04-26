package com.sysu.guli.service.oss.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@Data
@ConfigurationProperties(prefix = "aliyun.oss")//根据配置文件 前缀 自动获取配置文件配置的参数
public class OssProperties {
    private String endpoint;
    private String scheme ;
    private String accessKeyId ;
    private String accessKeySecret ;
    private String bucketName ;
}

package com.sysu.guli.service.vod.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.vod")
public class VodProperties {
    private String keyId;
    private String keySecret;
//    private String templateGroupId;
    private String workflowId;
    private String regionId;
}

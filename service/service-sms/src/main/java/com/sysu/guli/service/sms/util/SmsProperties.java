package com.sysu.guli.service.sms.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
//注意prefix要写到最后一个 "." 符号之前
@ConfigurationProperties(prefix="aliyun.sms")
public class SmsProperties {
    public String appSecret;//  = "vzIB7e79xwMy0LDS9mvdHisjMPaBGVl4";
    public String appCode;// = "8e45699f00ff4bdbb807664045acd813";
    public String host;// = "http://dingxin.market.alicloudapi.com";
    public String path;// = "/dx/sendSms";
    public String method;// = "POST";
    public String templateId;// = "TP1711063";

}
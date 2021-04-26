package com.sysu.guli.service.sms.service.impl;

import com.google.gson.Gson;
import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.result.ResultCodeEnum;
import com.sysu.guli.service.sms.service.SmsService;
import com.sysu.guli.service.base.util.HttpUtils;
import com.sysu.guli.service.sms.util.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    SmsProperties smsProperties;

    @Override
    public void sendCode(String mobile,String checkCode) {

        //调用短信服务api发送验证码给客户
        String host = smsProperties.host;
        String path = smsProperties.path;
        String method = smsProperties.method;
        String appcode = smsProperties.appCode;
        String templateId = smsProperties.templateId;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();//请求参数
        querys.put("mobile", mobile);
        querys.put("param", "code:" + checkCode);
        querys.put("tpl_id", templateId);
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String data = EntityUtils.toString(response.getEntity(), "UTF-8");
//            System.out.println("data = " + data);
            Gson gson = new Gson();
            HashMap<String,String> map = gson.fromJson(data, HashMap.class);
            String returnCode = map.get("return_code");
            if (!"00000".equals(returnCode)){
            throw new GuliException(ResultCodeEnum.SMS_SEND_ERROR);
            }else {
                log.warn(returnCode);
            }
        } catch (Exception e) {
                log.error(ExceptionUtils.getStackTrace(e));
            throw new GuliException(ResultCodeEnum.SMS_SEND_ERROR);
        }

    }
}

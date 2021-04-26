package com.sysu.guli.service.sms.controller;

import com.sysu.edu.common.util.FormUtils;
import com.sysu.edu.common.util.RandomUtils;
import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.base.result.ResultCodeEnum;
import com.sysu.guli.service.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;
@RestController
@Slf4j

@RequestMapping("/admin/sms")
public class SmsController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    SmsService smsService;

    @GetMapping("sendCode/{mobile}")
    public R sendCode(@PathVariable String mobile) {
        //校验手机号是否合法
        if (mobile == null || !FormUtils.isMobile(mobile)) {
            throw new GuliException(ResultCodeEnum.LOGIN_PHONE_ERROR);
        }
        //生成验证码
        String checkCode = RandomUtils.getSixBitRandom();
        //Redis缓存验证码
        String key = "checkCode::" + mobile;
        redisTemplate.opsForValue().set(key, checkCode, 10, TimeUnit.MINUTES);

        //记录验证码次数,一天只给发5次
        String codeCountKey = "codeCount::" + mobile;
        Object codeCount = redisTemplate.opsForValue().get(codeCountKey);
        if (codeCount == null) {
            redisTemplate.opsForValue().set(codeCountKey, 1, 1, TimeUnit.DAYS);
        } else {
            int count = Integer.parseInt(codeCount.toString());
            if (count < 5){
                redisTemplate.opsForValue().increment(codeCountKey);
            }else {
                throw new GuliException(ResultCodeEnum.SMS_SEND_ERROR_BUSINESS_LIMIT_CONTROL);
            }
        }
        //调用短信服务api发送验证码给客户
        smsService.sendCode(mobile, checkCode);
        return R.ok().setMessage("验证码发送成功!");
    }
}

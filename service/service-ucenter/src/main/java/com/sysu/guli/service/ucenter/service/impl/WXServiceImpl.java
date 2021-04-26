package com.sysu.guli.service.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.sysu.edu.common.util.HttpClientUtils;
import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.helper.JwtHelper;
import com.sysu.guli.service.base.helper.JwtInfo;
import com.sysu.guli.service.base.result.ResultCodeEnum;
import com.sysu.guli.service.base.util.HttpUtils;
import com.sysu.guli.service.ucenter.entity.Member;
import com.sysu.guli.service.ucenter.service.MemberService;
import com.sysu.guli.service.ucenter.service.WXService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.HttpClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Service
public class WXServiceImpl implements WXService {
    @Value("${wx.open.appId}")
    String appId;

    @Value("${wx.open.appSecret}")
    String appSecret;

    @Value("${wx.open.redirectUri}")
    String redirectUri;

    @Autowired
    MemberService memberService;

    @Override
    public String WXLogin(String state) {
        String Url = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect ";
        Url = String.format(Url, appId, redirectUri, state);
        return Url;
    }

    @Override
    public String getToken(String code, String state) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        url = String.format(url, appId, appSecret, code);
        HttpClientUtils httpClient = new HttpClientUtils(url);
        try {
            httpClient.get();
            String content = httpClient.getContent();
            System.out.println("content = " + content);
            // 解析content中的accessToken和openid
            Gson gson = new Gson();
            HashMap map = gson.fromJson(content, HashMap.class);
            String accessToken = (String) map.get("access_token");
            String openid = (String) map.get("openid");
            if (map.get("errcode") != null) {
                throw new GuliException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
            }
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("openid", openid);
            Member member = memberService.getOne(queryWrapper);
            if (member == null) {
                member = new Member();
                String url2 = "https://api.weixin.qq.com/sns/userinfo?" +
                        "access_token=%s" +
                        "&openid=%s";
                url2 = String.format(url2, accessToken, openid);
                HttpClientUtils httpClient2 = new HttpClientUtils(url2);
                httpClient2.get();
                String content2 = httpClient2.getContent();
                HashMap userInfo = gson.fromJson(content2, HashMap.class);
                if (userInfo.get("errcode") != null) {
                    throw new GuliException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
                }
                String avatar = (String) userInfo.get("headimgurl");
                System.out.println("avatar = " + avatar);
                int sex = (int) ((double) userInfo.get("sex"));
                String nickname = (String) userInfo.get("nickname");
                member.setAvatar(avatar);
                member.setSex(sex);
                member.setNickname(nickname);
                member.setOpenid(openid);
                memberService.save(member);
            }
            // 2、最后根据accesstoken和openid获取用户信息然后生成jwt

            JwtInfo jwtInfo = new JwtInfo();
            BeanUtils.copyProperties(member, jwtInfo);
            String token = JwtHelper.createToken(jwtInfo);
            return token;

        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GuliException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }
    }
}

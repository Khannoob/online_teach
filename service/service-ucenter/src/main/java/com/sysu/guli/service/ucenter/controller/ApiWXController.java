package com.sysu.guli.service.ucenter.controller;

import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.base.result.ResultCodeEnum;
import com.sysu.guli.service.ucenter.service.WXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/api/ucenter/wx")
public class ApiWXController {

    @Value("${server.port}")
    Integer port;
    @Autowired
    WXService wxService;
    @ResponseBody
    @GetMapping("set")
    public R setV(HttpSession httpSession){
        System.out.println("保存到session的port = " + port);
        httpSession.setAttribute("sKey", "sVal111");
        HashMap<String, Object> map = new HashMap<>();
        map.put("设置sKey","");
        map.put("设置sKey的端口", port);
        return R.ok().data(map);
    }
    @ResponseBody
    @GetMapping("get")
    public Map getV(HttpSession httpSession){
        Object sKey = httpSession.getAttribute("sKey");
        System.out.println("读取session的port = " + port+"sKey对应的值"+sKey);
        HashMap<String, Object> map = new HashMap<>();
        map.put("获取sKey",sKey);
        map.put("获取sKey的端口",port);
        return map;
    }

    @GetMapping("login")
    public String wxLogin(HttpSession httpSession){
        String state = UUID.randomUUID().toString().replace("-", "");
        httpSession.setAttribute("wxState", state);
        String Url = wxService.WXLogin(state);

        return "redirect:"+Url;
    }

    @GetMapping("callback")
    public String callback(String code,String state,HttpSession httpSession){
        Object wxState = httpSession.getAttribute("wxState");
        if (StringUtils.isEmpty(code)||!state.equals(wxState)){
            throw new GuliException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }

        String token = wxService.getToken(code,state);
        return "redirect:http://localhost:3000/?guli_token="+token;
    }
}

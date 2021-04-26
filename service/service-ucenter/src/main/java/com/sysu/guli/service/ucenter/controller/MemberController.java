package com.sysu.guli.service.ucenter.controller;


import com.sysu.guli.service.base.helper.JwtHelper;
import com.sysu.guli.service.base.helper.JwtInfo;
import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.ucenter.entity.RegisterForm;
import com.sysu.guli.service.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author khannoob
 * @since 2021-04-23
 */

@RestController
@RequestMapping("/admin/ucenter/member")
public class MemberController {
    @Autowired
    MemberService memberService;

    @PostMapping("regist")
    public R regist(@RequestBody RegisterForm registerForm){
        memberService.regist(registerForm);
        return R.ok().setMessage("注册成功!");
    }
    @PostMapping("login")
    public R login(String mobile,String password){
        if (mobile == null || password == null) {
            return R.error().setMessage("账户或密码不能为空");
        }
        String token = memberService.login(mobile,password);
        return R.ok().data("token", token);
    }
    @GetMapping("getJwtInfo")
    public R getJwtInfo(HttpServletRequest request){
        JwtInfo jwtInfo = JwtHelper.getJwtInfo(request);
        return R.ok().data("userInfo", jwtInfo);
    }
}


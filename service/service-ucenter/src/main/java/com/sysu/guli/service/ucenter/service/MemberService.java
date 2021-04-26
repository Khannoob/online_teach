package com.sysu.guli.service.ucenter.service;

import com.sysu.guli.service.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sysu.guli.service.ucenter.entity.RegisterForm;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-23
 */
public interface MemberService extends IService<Member> {

    void regist(RegisterForm registerForm);

    String login(String mobile, String password);
}

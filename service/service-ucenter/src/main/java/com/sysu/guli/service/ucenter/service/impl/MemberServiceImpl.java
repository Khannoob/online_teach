package com.sysu.guli.service.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sysu.edu.common.util.MD5;
import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.helper.JwtHelper;
import com.sysu.guli.service.base.helper.JwtInfo;
import com.sysu.guli.service.base.result.ResultCodeEnum;
import com.sysu.guli.service.ucenter.entity.Member;
import com.sysu.guli.service.ucenter.entity.RegisterForm;
import com.sysu.guli.service.ucenter.mapper.MemberMapper;
import com.sysu.guli.service.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-23
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void regist(RegisterForm registerForm) {
        String code = registerForm.getCode();
        String mobile = registerForm.getMobile();
        String checkCode = "checkCode::" + mobile;
        String nickname = registerForm.getNickname();
        String password = registerForm.getPassword();
        if (!StringUtils.isEmpty(code) &&
                !StringUtils.isEmpty(mobile) &&
                !StringUtils.isEmpty(nickname) &&
                !StringUtils.isEmpty(password)) {
            LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Member::getMobile, mobile);
            Integer count = baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new GuliException(ResultCodeEnum.REGISTER_MOBLE_ERROR);
            }
            Object redisCode = redisTemplate.opsForValue().get(checkCode);
            if (!code.equals(redisCode)) {
                throw new GuliException(ResultCodeEnum.CODE_ERROR);
            }

            //给密码加密
            password = MD5.encrypt(password);
            registerForm.setPassword(password);
            Member member = new Member();
            BeanUtils.copyProperties(registerForm, member);
            baseMapper.insert(member);
        }
    }

    @Override
    public String login(String mobile, String password) {

        LambdaQueryWrapper<Member> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Member::getMobile, mobile);
        Member member = baseMapper.selectOne(lambdaQueryWrapper);
        if (member==null){
            throw new GuliException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }
        if (member.getDisabled()){
            throw new GuliException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }
        String encrypt = MD5.encrypt(password);
        if (!encrypt.equals(member.getPassword())){
            throw new GuliException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        JwtInfo jwtInfo = new JwtInfo();
        BeanUtils.copyProperties(member, jwtInfo);
        String token = JwtHelper.createToken(jwtInfo);

        return token;
    }
}

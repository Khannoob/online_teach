package com.sysu.guli.service.ucenter.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户注册对象")
public class RegisterForm {
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "电话号码")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "验证码")
    private String code;
}
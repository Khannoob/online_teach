package com.sysu.guli.service.base.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
@ApiModel(value = "全局统一返回结果")
@NoArgsConstructor
public class R {
    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回的信息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();//记得默认初始化

    //根据状态码设置Result
    public static R setResult(ResultCodeEnum resultCodeEnum) {
        R r = new R();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }
    //设置成功方法ok()
    public static R ok(){
        //直接调用上面SetResult方法实现代码的复用
       return R.setResult(ResultCodeEnum.SUCCESS);
    }
    //设置通用的失败方法unknown error
    public static R error(){
        return R.setResult(ResultCodeEnum.UNKNOWN_REASON);
    }
    //如果成功设置返回的data
    //1.在原有的map上追加
    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
    //2.覆盖原有的Map
    public R data(Map<String,Object> map){
        this.data = map;
        return this;
    }
}

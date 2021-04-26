package com.sysu.guli.service.base.handler;

import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.result.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice//异常处理的注解
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody//是将java对象转为json格式的数
    public R error(Exception e){
        //进行日志输出日志文件
        log.error(ExceptionUtils.getStackTrace(e));
        e.printStackTrace();
        return R.error();
    }
    //BadSqlGrammarException
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody//是将java对象转为json格式的数
    public R error(BadSqlGrammarException e){
        e.printStackTrace();
        return R.error().setMessage("sql语法错误");
    }
    //HttpMessageNotReadableException
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody//是将java对象转为json格式的数
    public R error(HttpMessageNotReadableException e){
        e.printStackTrace();
        return R.error().setMessage("前端json语法错误");
    }

    //HttpMessageNotReadableException
    @ExceptionHandler(GuliException.class)
    @ResponseBody//是将java对象转为json格式的数
    public R error(GuliException e){
//        e.printStackTrace();
        log.error(ExceptionUtils.getStackTrace(e));
        return R.setResult(e.getResultCodeEnum());
    }
}

package com.sysu.guli.infrastructure.apigateway.handler;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;

public class GlobalJsonExceptionHandler extends DefaultErrorWebExceptionHandler {
    public GlobalJsonExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }
    //配置异常信息
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        int code = 500;
        Throwable error = getError(request);
        if(error instanceof org.springframework.cloud.gateway.support.NotFoundException){
            code = 404;
        }
        String message  = error.getMessage();
        return response(code , message);
    }
    //封装异常响应数据
    public static Map<String, Object> response(int code, String errorMessage) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", errorMessage);
        map.put("data", null);
        return map;
    }
    //指定异常响应采用Json处理
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all() ,this::renderErrorResponse );
    }
    //响应报文获取状态码
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
//        int statusCode = (int) errorAttributes.get("code");
        return 200;
    }
}

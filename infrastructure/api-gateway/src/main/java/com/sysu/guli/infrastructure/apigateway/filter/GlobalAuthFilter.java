package com.sysu.guli.infrastructure.apigateway.filter;

import com.google.gson.JsonObject;
import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.helper.JwtHelper;
import com.sysu.guli.service.base.result.ResultCodeEnum;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    //全局过滤器方法
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1、判断当前访问的路径是否需要鉴权
        //只针对路径为/api/**/auth/** 的请求地址进行鉴权，鉴权成功和不需要鉴权的请求直接放行
        //ServerHttpRequest属于 WebFlux中的API 和传统的servlet编程的HttpServletRequest 一样代表请求报文，但是方法不一样
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //获取访问的服务器后的path地址
        String path = request.getURI().getPath();
        AntPathMatcher matcher = new AntPathMatcher();
        boolean match = matcher.match("/api/**/auth/**", path);

        //2、不需要鉴权的请求进行放行
        if (!match){
            return chain.filter(exchange);
        }

        //3、对需要鉴权的请求进行鉴权 检查请求头里面的token
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("token");
        //public static Claims checkToken(HttpServletRequest request) 参数不同,不能使用此方法 只能选择String类型
        boolean b = JwtHelper.checkToken(token);
        //4、鉴权成功放行
        if (b){
            return chain.filter(exchange);
        }

        //5、鉴权失败：给请求一个错误的响应，拒绝放行
        // 成功放行交给目标微服务生成响应报文，失败拦截需要我们手动设置一个响应报文给前端
        return getVoidMono(response);
    }

    //设置错误响应的格式=====>手动设置一个响应报文
    private Mono<Void> getVoidMono(ServerHttpResponse response) {
        //设置响应报文是json格式
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", ResultCodeEnum.LOGIN_AUTH.getCode());
        jsonObject.addProperty("message", "收费视频请先登录购买!");
        jsonObject.addProperty("success", ResultCodeEnum.LOGIN_AUTH.getSuccess());
        String rJson = jsonObject.toString();
        DataBuffer buffer = response.bufferFactory().wrap(rJson.getBytes());
        //把json字符串 存到 返回的响应体
        Mono<DataBuffer> body = Mono.just(buffer);
        return response.writeWith(body);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

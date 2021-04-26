package com.sysu.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

public class JwtTest {
    //过期时间，毫秒，30分钟
    private static long tokenExpiration = 10*60*1000;
    //秘钥
    private static String tokenSignKey = "123456";

    @Test
    public void testCreateToken(){
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT") //令牌类型
                .setHeaderParam("alg", "HS256") //签名算法
                .setSubject("guli-user") //令牌主题
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) //过期时间

                .claim("nickname", "Helen")
                .claim("avatar", "1.jpg")

                .signWith(SignatureAlgorithm.HS256, tokenSignKey)//签名哈希
                .compact(); //转换成字符串

        System.out.println(token);
    }


}

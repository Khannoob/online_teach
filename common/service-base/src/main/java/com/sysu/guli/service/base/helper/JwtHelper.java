package com.sysu.guli.service.base.helper;

import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.result.ResultCodeEnum;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtHelper {

    //过期时间，毫秒，一天
    private static long tokenExpiration = 24 * 60 * 60 * 1000;
    //秘钥
    private static String tokenSignKey = "123456";

    private static Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] bytes = DatatypeConverter.parseBase64Binary(tokenSignKey);
        return new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());
    }

    public static String createToken(JwtInfo jwtInfo) {

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("GULI-USER")//主题
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("id", jwtInfo.getId())//用户id
                .claim("nickname", jwtInfo.getNickname())//用户昵称
                .claim("avatar", jwtInfo.getAvatar())//用户头像
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     *
     * @param request
     * @return
     */
    public static Claims checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new GuliException(ResultCodeEnum.LOGIN_AUTH);
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return claims;
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GuliException(ResultCodeEnum.LOGIN_AUTH);
        }
    }

    /**
     * 判断token是否有效
     *
     * @param token
     * @return
     */
    public static boolean checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据token获取会员id
     *
     * @param request
     * @return
     */
    public static String getId(HttpServletRequest request) {
        Claims claims = checkToken(request);
        return (String) claims.get("id");
    }

    /**
     * 根据token获取会员信息
     *
     * @param request
     * @return
     */
    public static JwtInfo getJwtInfo(HttpServletRequest request) {
        JwtInfo jwtInfo = new JwtInfo();
        Claims claims = checkToken(request);
        if (claims.get("id") != null) {
            jwtInfo.setId(claims.get("id").toString());
        }
        if (claims.get("nickname") != null) {
            jwtInfo.setNickname(claims.get("nickname").toString());
        }
        if (claims.get("avatar") != null) {
            jwtInfo.setAvatar(claims.get("avatar").toString());
        }
        return jwtInfo;
    }

    public static void main(String[] args) {

        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId("1");
        jwtInfo.setNickname("Helen");
        jwtInfo.setAvatar("1.jpg");

        String token = JwtHelper.createToken(jwtInfo);
        System.out.println(token);
    }
}

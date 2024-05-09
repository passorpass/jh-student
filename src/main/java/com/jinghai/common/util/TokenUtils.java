package com.jinghai.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;


@Slf4j
public final class TokenUtils {


    /**
     * @param secretKey username
     * @param claims     user role
     * @return The encrypted token
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定签名的时候使用的签名算法，也就是header那部分
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);


        // 设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置过期时间
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * @param username username
     * @param role     user role
     * @return The encrypted token
     */
    /**
     * Token解密
     *
     * @param secretKey jwt秘钥 此秘钥一定要保留好在服务端, 不能暴露出去, 否则sign就可以被伪造, 如果对接多个客户端建议改造成多个
     * @param token     加密后的token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
        //获取的token需要截取主要信息内容
        token = token.substring(7);
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户id
     */
    public static String getAdminId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("admin").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secretKey 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secretKey) {
        try {
            log.info("进入token验证{}",token.substring(7));
            token = token.substring(7);
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("admin", username)
                    .build();
            //效验TOKEN
            verifier.verify(token);
            System.out.println("登录认证成功!");
            return true;
        } catch (Exception exception) {
            System.out.println("JwtUtil登录认证失败!");
            log.info("捕获的异常是 =>{}",exception.getMessage());
            return false;
        }
    }

}

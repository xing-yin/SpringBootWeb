package com.alany.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author yinxing
 * @date 2019/3/21
 */

public class JWTUtil {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // 过期时间5分钟
    private static final long EXPRIRE_TIME = 5 * 60 * 1000;

    /**
     * 生成签名，5分钟后过期
     *
     * @param username
     * @param secret
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String sign(String username, String secret) throws UnsupportedEncodingException {
        Date date = new Date(System.currentTimeMillis() + EXPRIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }


    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username", username)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return true;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username").asString();
    }


}

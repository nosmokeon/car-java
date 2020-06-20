package com.lyc.carjava.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class JWTUtil {
    public static final  String secret = "ptgsecret";

    /**
     * 生成token
     * @param userName 加入token的内容
     * @param secret 加密算法的秘钥
     * @return
     */
    public static String sign(String userName,String secret)
    {
        //设置过期时间:15天后
        Date expireTime = new Date(System.currentTimeMillis()+RedisUtil.EXPIRE_TIME);
        //设置加密算法
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //添加请求头
        Map<String,Object> headers = new LinkedHashMap<>();
        headers.put("timestamp", String.valueOf(System.currentTimeMillis()));
        //生成token
        String token = JWT.create().withHeader(headers).withClaim("userName",userName).withExpiresAt(expireTime).sign(algorithm);
        return token;
    }

    /**
     * 验证token是否有效
     * @param token
     * @param secret 加密算法的秘钥
     * @return
     */
    public static boolean verify(String token,String secret)
    {
        //设置加密算法
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            // 根据加密算法生成JWT效验器
            JWTVerifier verifier = JWT.require(algorithm).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 验证token是否过期
     * @param token
     * @return
     */
    public static boolean isOverdue(String token)
    {
        //解密
        DecodedJWT decodedJWT = JWT.decode(token);
        //查看token是否过期
        if(decodedJWT.getExpiresAt().after(new Date()))
        {

            return true;
        }
        else
        {

            return false;
        }
    }

    /**
     * 提取token中的userName
     * @param token
     * @return
     */
    public static String getUserName(String token)
    {
        //解码的token
        DecodedJWT decodedJWT = JWT.decode(token);
        //提取在token中的userName
        return decodedJWT.getClaim("userName").asString();
    }
}

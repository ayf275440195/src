package com.ayf.springboot_jwt_login.utils;


import com.alibaba.fastjson.JSONObject;
import com.ayf.springboot_jwt_login.exceptions.ZDException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    // 这里是加密解密的key。
    private static final  Key key = MacProvider.generateKey();

    // 设置算法（必须）
    private static final SignatureAlgorithm HS512 = SignatureAlgorithm.HS512;

    private static final String SUBJECT = "Joe";


    public static String createToken(JSONObject user){
        Date date = new Date();

        // 生成jwt
        String compactJws = Jwts.builder()
                .setIssuedAt(date)//签发时间
                .setNotBefore(date)//生效时间
                .setExpiration(new Date(date.getTime() + 1000 * 60))//过期时间
                .setSubject(SUBJECT)// 设置主题
                .claim("studentId", user)// 添加自定义数据
                .signWith(HS512, key)// 设置算法（必须）
                .compact();// 这个是全部设置完成后拼成jwt串的方法
        return compactJws;
    }

    public static Object analysisToken(String token) throws ZDException {
        System.out.println(token);
        Object studentId = null;
        // 解析jwt
        try {
            // compactJws为jwt字符串
            Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            Claims body = parseClaimsJws.getBody();// 得到body后我们可以从body中获取我们需要的信息
            String subject = body.getSubject();
            studentId = body.get("studentId");
            System.out.println("主题: " + subject);
            System.out.println("自定义数据: " + studentId);
            System.out.println(body.getExpiration());
            System.out.println(body.getIssuedAt());

            // OK, we can trust this JWT

        } catch (SignatureException | MalformedJwtException e) {
            System.out.println("解析错误");
            throw new ZDException("解析错误");
            // TODO: handle com.ayf.springboot_jwt_login.exceptions
            // don't trust the JWT!
            // jwt 解析错误
        } catch (ExpiredJwtException e) {
            System.out.println("登录超时");
            throw new ZDException("登录超时");
            // TODO: handle com.ayf.springboot_jwt_login.exceptions
            // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
        }
        return studentId;
    }


    public static void main(String[] args) {
        JSONObject user = new JSONObject();
        user.put("name","tom");
        user.put("age",22);
        user.put("add","usa");
        String t = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1NzQzMjAwNDQsInN1YiI6IkpvZSIsInN0dWRlbnRJZCI6eyJhZGQiOiJ1c2EiLCJuYW1lIjoidG9tIiwiYWdlIjoyMn19.xSjYdkfXYIAyB5zv18fScPdMNPgwjvvNzKKRRmzdXAIF4jgc6gQ3JnAclKLrs8eDCDPbOmLOaAqkJFdXUszRQw";
        String token = createToken(user);

        try {
            Object o = analysisToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

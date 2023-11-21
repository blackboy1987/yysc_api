package com.bootx.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class JWTUtils {

    // 主题
    public static final String SUBJECT = "RookieLi";

    // 秘钥
    public static final String SECRETKEY = "hanjiayang5211314119950130blackboy198710061130";

    // 过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24 * 7;

    public static String create(String id, Map<String,Object> map){
        JwtBuilder builder= Jwts.builder()
                .id(id)
                .subject(SUBJECT)
                .issuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(SECRETKEY.getBytes()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRE));
        for (String key: map.keySet()) {
            builder.claim(key,map.get(key));
        }
        return builder.compact();
    }

    public static Claims parseToken(String token){
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRETKEY.getBytes());
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getKey(String token,String key){
        try {
            Claims claims = parseToken(token);
            return claims.get(key).toString();
        }catch (Exception ignored){
        }
        return null;
    }


    public static void main(String[] args) {
        String s = create("123", new HashMap<>());
        System.out.println(s);
        Claims claims = parseToken(s);
        System.out.println(claims.getId());
    }
}

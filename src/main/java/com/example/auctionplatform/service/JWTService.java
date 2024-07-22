package com.example.auctionplatform.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.auctionplatform.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTService {
    @Value("${jwt.my.throwTime}")
    public static int throwTime;
    @Value("${jwt.my.secret}")
    public static String secret;
    public static String getToken(UserDTO userDTO){
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDTO.getId());
        claims.put("Nickname", userDTO.getNickname());
        claims.put("Email", userDTO.getEmail());
        claims.put("phone", userDTO.getPhone());

        return JWT.create()
                .withClaim("user",claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + throwTime))
                .sign(Algorithm.HMAC256(secret));
    }
}

package com.example.auctionplatform.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.auctionplatform.dto.UserDTO;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTService {

    public static String getToken(UserDTO userDTO, int throwTime, String secret){
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
    public static Map<String, Object> parseToken(String token, String secret){
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getClaim("user")
                .asMap();

    }
}

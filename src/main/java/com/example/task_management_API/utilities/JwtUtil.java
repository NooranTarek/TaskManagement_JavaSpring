package com.example.task_management_API.utilities;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtUtil {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private Long expirationTime;

    public String generateToken(String username, String role , Integer id){
        return Jwts.builder()
                .setSubject(username)
                .claim("role",role)
                .claim("id",id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

    }
    public String extractUserName(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public Integer extractUserId(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("id", Integer.class);
    }
    public String extractRole(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
    public boolean validateToken(String token,String username){
        String extractedUsername=extractUserName(token);
        return extractedUsername.equals(username)&&!isTokenExpired(token);
    }
    private boolean isTokenExpired (String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration (String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

}

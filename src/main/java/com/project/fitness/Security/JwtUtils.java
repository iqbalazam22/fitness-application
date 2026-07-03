package com.project.fitness.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final long jwtExpirationMs = 172800000; // 2 days

    //  SINGLE SOURCE OF TRUTH (Base64 key)
    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    //  Extract JWT from header
    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    //  Generate token
    public String generateToken(String userId, String role){
        return Jwts.builder()
                .subject(userId)
                .claim("roles", List.of(role)) // 🔥 store STRING, not object
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSecretKey())
                .compact();
    }

    //  Validate token
    public boolean validateJwtToken(String jwtToken){
        try{
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(jwtToken);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //  Extract userId
    public String getUserIdFromToken(String jwt){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }

    //  Extract all claims
    public Claims getAllClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}

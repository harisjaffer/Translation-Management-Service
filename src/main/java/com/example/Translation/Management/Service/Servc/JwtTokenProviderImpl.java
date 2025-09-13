package com.example.Translation.Management.Service.Servc;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Translation.Management.Service.Repository.TokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtTokenProviderImpl implements TokenProvider {

    @Value("${jwt.secret:changeit}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}") // default 24h
    private long expirationMs;

    @Override
    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> extractRoles(String token) {
        Claims c = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        Object roles = c.get("roles");
        if (roles instanceof List) {
            return (List<String>) roles;
        }
        return java.util.Collections.emptyList();
    }
}
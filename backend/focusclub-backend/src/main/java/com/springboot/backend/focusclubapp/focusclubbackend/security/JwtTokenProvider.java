package com.springboot.backend.focusclubapp.focusclubbackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final Key key;
    private final int jwtExpirationInMs;

    public JwtTokenProvider(@Value("${app.jwt-expiration-ms}") int jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Genera una clave segura automáticamente
    }

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512) // Usa la clave segura generada
                .compact();
    }

    public String getUserEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            logger.debug("Validating JWT token: {}", authToken);
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
            logger.debug("JWT token is valid");
            return true;
        } catch (JwtException ex) {
            logger.error("JWT token validation failed", ex);
        }
        return false;
    }
}

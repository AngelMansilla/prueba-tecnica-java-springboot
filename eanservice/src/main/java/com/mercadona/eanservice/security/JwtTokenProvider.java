package com.mercadona.eanservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {

    @Autowired
    private JwtConfig jwtConfig;

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpirationTime());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        byte[] keyBytes = jwtConfig.getSecret().getBytes();
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            ((JwtParserBuilder) Jwts.builder())
                    .verifyWith(getSignInKey())
                    .build()
                    . parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            System.err.println("Token validation error: " + e.getMessage());
        }
        return false;
    }
}

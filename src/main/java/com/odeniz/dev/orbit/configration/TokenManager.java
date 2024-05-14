package com.odeniz.dev.orbit.configration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenManager {

    public static final Long validity = 30 * 60 * 1000L;
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(Authentication authentication) {
        JWTUserDetail userDetail = (JWTUserDetail) authentication.getPrincipal();
        Date expiredDate = new Date(System.currentTimeMillis() + validity);
        return Jwts.builder()
                .setSubject(Long.toString(userDetail.getId()))
                .setIssuer("dev_orbit_by_o_deniz")
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(key)
                .compact();
    }

    boolean isValidate(String token) {
        return getUserToken(token) != null && isExpired(token);
    }

    public String getUserToken(String token) {
        return getClaims(token).getSubject();
    }

    private boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public Long getUserIdFromJwt(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

}
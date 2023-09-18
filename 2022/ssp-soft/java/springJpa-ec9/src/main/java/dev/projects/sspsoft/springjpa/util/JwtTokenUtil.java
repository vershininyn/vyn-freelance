package dev.projects.sspsoft.springjpa.util;

import dev.projects.sspsoft.springjpa.domain.TokenPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_ACCESS_TOKEN_VALIDITY = 365*24*60*60;
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 365*24*60*60;

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public TokenPair generateToken(String username) {
        Map<String, Object> accessClaims = new HashMap<>();
        accessClaims.put("typ", "Bearer");
        String accessToken = doGenerateToken(accessClaims, username, JWT_ACCESS_TOKEN_VALIDITY);

        Map<String, Object> refreshClaims = new HashMap<>();
        refreshClaims.put("typ", "Refresh");
        String refreshToken = doGenerateToken(refreshClaims, username, JWT_REFRESH_TOKEN_VALIDITY);

        return new TokenPair(Map.of(accessToken, getAllClaimsFromToken(accessToken)),
                Map.of(refreshToken, getAllClaimsFromToken(refreshToken)));
    }

    public TokenPair regenerateTokenPair(String accessToken) {
        String username = getUsernameFromToken(accessToken);

        return generateToken(username);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, long jwtTokenValidity) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}

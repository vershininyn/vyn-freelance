package dev.projects.sspSoft.springSecurity.domain;

import io.jsonwebtoken.Claims;

import java.io.Serializable;
import java.util.Map;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private String jwttoken;

    private long jwtTokenExpiredTime;

    private String refreshToken;
    private long refreshTokenExpiredTime;

    public JwtResponse() {}

    public JwtResponse(Map<String, Claims> accessClaims, Map<String, Claims> refreshClaims) {
        jwttoken = accessClaims.keySet().stream().findFirst().get();
        jwtTokenExpiredTime = accessClaims.get(jwttoken).getExpiration().getTime();

        refreshToken = refreshClaims.keySet().stream().findFirst().get();
        refreshTokenExpiredTime = refreshClaims.get(refreshToken).getExpiration().getTime();
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public void setJwttoken(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public long getJwtTokenExpiredTime() {
        return jwtTokenExpiredTime;
    }

    public void setJwtTokenExpiredTime(long jwtTokenExpiredTime) {
        this.jwtTokenExpiredTime = jwtTokenExpiredTime;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getRefreshTokenExpiredTime() {
        return refreshTokenExpiredTime;
    }

    public void setRefreshTokenExpiredTime(long refreshTokenExpiredTime) {
        this.refreshTokenExpiredTime = refreshTokenExpiredTime;
    }
}

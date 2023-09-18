package dev.projects.sspSoft.springSecurity.domain;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class TokenPair {
    private Map<String, Claims> accessTokenClaims;
    private Map<String, Claims> refreshTokenClaims;
}

package dev.projects.sspSoft.springSecurity.controller;

import dev.projects.sspSoft.springSecurity.domain.JwtRequest;
import dev.projects.sspSoft.springSecurity.domain.JwtResponse;
import dev.projects.sspSoft.springSecurity.domain.TokenPair;
import dev.projects.sspSoft.springSecurity.service.AccountDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        produces = "application/json")
public class JwtAuthenticationController {
    private AuthenticationManager authenticationManager;

    private AccountDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager, AccountDetailsService accountDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = accountDetailsService;
    }

    @Operation(summary = "Создание аутентификационного JWT токена")
    @PostMapping(value = "/authenticate")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final TokenPair tokenPair = userDetailsService.generateTokenPair(authenticationRequest.getUsername());

        return ResponseEntity.ok(new JwtResponse(tokenPair.getAccessTokenClaims(), tokenPair.getRefreshTokenClaims()));
    }

    @Operation(summary = "Перегенерация пары токенов")
    @GetMapping(value = "/refresh")
    public ResponseEntity<JwtResponse> refreshTokenPair(@RequestParam("accesstoken") String accessToken) throws Exception {
        final TokenPair tokenPair = userDetailsService.regenerateTokenPair(accessToken);

        return ResponseEntity.ok(new JwtResponse(tokenPair.getAccessTokenClaims(), tokenPair.getRefreshTokenClaims()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch(AuthenticationException e) {
            throw new Exception(e);
        }
    }
}

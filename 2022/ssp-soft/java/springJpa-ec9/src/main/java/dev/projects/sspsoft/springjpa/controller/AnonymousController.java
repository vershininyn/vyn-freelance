package dev.projects.sspsoft.springjpa.controller;

import dev.projects.sspsoft.springjpa.domain.JwtRequest;
import dev.projects.sspsoft.springjpa.domain.UserEntity;
import dev.projects.sspsoft.springjpa.service.AccountDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/anon",
        produces = "application/json")
public class AnonymousController {
    private AuthenticationManager authenticationManager;
    private final AccountDetailsService userDetailsService;

    public AnonymousController(AuthenticationManager authenticationManager, AccountDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Tag(name = "Контроллер работы с анонимными пользователем", description = "Регистрация нового пользователя")
    @Operation(summary = "Регистрация анонимного пользователя")
    @PostMapping(value = "/register")
    public ResponseEntity<HttpStatus> registerAnonymousUser(@RequestBody JwtRequest request) {
        userDetailsService.registerNewUser(request.getUsername(), request.getPassword());

        return ResponseEntity.created(null).build();
    }

    @Tag(name = "Контроллер работы с анонимными пользователем", description = "Регистрация нового пользователя")
    @Operation(summary = "Регистрация анонимного пользователя")
    @PostMapping(value = "/auth")
    public ResponseEntity<UserEntity> authAnonymousUser(@RequestBody JwtRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());

        UserEntity user = userDetailsService.getUserByUsername(request.getUsername());

        return ResponseEntity.created(null).body(user);
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

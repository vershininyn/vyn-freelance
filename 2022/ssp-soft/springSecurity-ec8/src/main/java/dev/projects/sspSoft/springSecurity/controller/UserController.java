package dev.projects.sspSoft.springSecurity.controller;

import dev.projects.sspSoft.springSecurity.domain.UserEntity;
import dev.projects.sspSoft.springSecurity.service.AccountDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/user",
        produces = "application/json")
public class UserController {
    private final AccountDetailsService userService;

    public UserController(AccountDetailsService userService) {
        this.userService = userService;
    }

    @Tag(name = "Контроллер работы с пользователем", description = "Просмотр информации пользователя о себе")
    @Operation(summary = "Просмотр информации пользователя о себе")
    @GetMapping(value = "/me")
    public ResponseEntity<UserEntity> getUserInformation(@RequestParam("token") String token) {
        UserEntity user = null;

        try{
            user = userService.getUserByAccessToken(token);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @Tag(name = "Контроллер работы с пользователем", description = "Изменение почтового адреса пользователя")
    @Operation(summary = "Изменение почтового адреса пользователя")
    @PutMapping(value = "/email")
    public ResponseEntity<UserEntity> updateUserEmail(@RequestParam("token") String token, @RequestParam("email") String newEmail) {
        UserEntity user = null;

        try{
            user = userService.getUserByAccessToken(token);
            user.setEmail(newEmail);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

}

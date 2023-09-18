package dev.projects.sspsoft.springjpa.controller;

import dev.projects.sspsoft.springjpa.domain.UserEntity;
import dev.projects.sspsoft.springjpa.service.AccountDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        value = "/admin",
        produces = "application/json")
public class AdminController {
    private final AccountDetailsService userService;

    public AdminController(AccountDetailsService userService) {
        this.userService = userService;
    }

    @Tag(name = "Контроллер ADMIN работы с пользователем", description = "Просмотр информации пользователя о себе")
    @Operation(summary = "Просмотр информации пользователя о себе")
    @GetMapping(value = "/me")
    public ResponseEntity<UserEntity> getUserInformation(@RequestParam("token") String accessToken) {
        UserEntity user = null;

        try{
            user = userService.getUserByAccessToken(accessToken);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @Tag(name = "Контроллер ADMIN работы с пользователем", description = "Просмотр информации пользователей о себе")
    @Operation(summary = "Просмотр информации пользователей о себе")
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserEntity>> getUsersInformation() {
        return ResponseEntity.ok(userService.getAllUsersInformation());
    }

    @Tag(name = "Контроллер работы с пользователем", description = "Изменение почтового адреса пользователя")
    @Operation(summary = "Изменение почтового адреса пользователя")
    @PutMapping(value = "/email")
    public ResponseEntity<UserEntity> updateUserEmail(@RequestParam("token") String accessToken, @RequestParam("email") String newEmail) {
        UserEntity user = null;

        try{
            user = userService.getUserByAccessToken(accessToken);
            user.setEmail(newEmail);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @Tag(name = "Контроллер ADMIN работы с пользователем", description = "Удаление пользователя")
    @Operation(summary = "Удаление пользователя")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam("token") String accessToken) {
        userService.deleteUserByToken(accessToken);

        return ResponseEntity.noContent().build();
    }
}

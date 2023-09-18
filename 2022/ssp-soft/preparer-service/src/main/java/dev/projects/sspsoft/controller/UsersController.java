package dev.projects.sspsoft.controller;

import dev.projects.sspsoft.model.dto.ApiShipLoginRequestDto;
import dev.projects.sspsoft.model.dto.ApiShipSignupRequestDto;
import dev.projects.sspsoft.model.entity.ApiShipUserEntity;
import dev.projects.sspsoft.model.entity.ApiShipUserTokenEntity;
import dev.projects.sspsoft.service.apiship.api.ApiShipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UsersController {

    private final ApiShipService apiShipService;

    public UsersController(ApiShipService apiShipService) {
        this.apiShipService = apiShipService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiShipUserEntity> signup(@RequestBody ApiShipSignupRequestDto requestDto) {
        final ApiShipUserEntity response = apiShipService.signup(requestDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiShipUserTokenEntity> login(@RequestBody ApiShipLoginRequestDto requestDto) {
        final ApiShipUserTokenEntity response = apiShipService.login(requestDto);

        return ResponseEntity.ok(response);
    }
}
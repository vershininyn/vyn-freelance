package dev.projects.sspsoft.apiship.api;

import dev.projects.sspsoft.apiship.model.City;
import dev.projects.sspsoft.apiship.service.ApiShipService;
import dev.projects.sspsoft.apiship.service.TokenApiShipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        value = "/city",
        produces = "application/json")
public class Api {
    private TokenApiShipService tokenService;
    private ApiShipService apiShipService;

    public Api(TokenApiShipService tokenService, ApiShipService apiShipService) {
        this.tokenService = tokenService;
        this.apiShipService = apiShipService;
    }

    @GetMapping(value = "/getByToken")
    public ResponseEntity<List<City>> getCity(@RequestParam(value = "login") String login, @RequestParam(value = "password") String password) {
        String token = tokenService.getAuthTokenByCredentials(login, password);

        List<City> city = apiShipService.getCityListByToken(token);

        return ResponseEntity.ok(city);
    }
}

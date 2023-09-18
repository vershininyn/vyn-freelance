package dev.projects.sspsoft.apiship.clients;

import dev.projects.sspsoft.apiship.model.City;
import dev.projects.sspsoft.apiship.model.CredentialsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "api-ship-client", url = "https://api.apiship.ru/v1/")
public interface ApiShipHttpClient {
    @PostMapping(value = "/users/login")
    String getTokenByCredentialsBodyPost(@RequestBody CredentialsDTO dto);

    @GetMapping(value = "/lists/providerCities/cdek?limit=10&offset=0")
    List<City> getCitesByTokenGet(@RequestParam("token") String token);
}

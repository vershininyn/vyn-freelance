package dev.projects.sspsoft.apiship.service;

import dev.projects.sspsoft.apiship.clients.ApiShipHttpClient;
import dev.projects.sspsoft.apiship.model.City;
import dev.projects.sspsoft.apiship.model.CredentialsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiShipService {
    private final ApiShipHttpClient httpClient;

    public ApiShipService(ApiShipHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public List<City> getCityListByToken(String token) {
        return httpClient.getCitesByTokenGet(token);
    }
}

package dev.projects.sspsoft.apiship.service;

import dev.projects.sspsoft.apiship.clients.ApiShipHttpClient;
import dev.projects.sspsoft.apiship.model.CredentialsDTO;
import org.springframework.stereotype.Service;

@Service
public class TokenApiShipService {
    private final ApiShipHttpClient httpClient;

    public TokenApiShipService(ApiShipHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getAuthTokenByCredentials(String login, String password) {
        return httpClient.getTokenByCredentialsBodyPost(new CredentialsDTO(login, password));
    }
}

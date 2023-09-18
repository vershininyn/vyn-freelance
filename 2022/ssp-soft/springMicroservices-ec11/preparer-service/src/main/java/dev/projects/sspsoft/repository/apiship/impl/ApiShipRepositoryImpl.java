package dev.projects.sspsoft.repository.apiship.impl;

import dev.projects.sspsoft.model.dto.ApiShipDefaultProviderCitiesDto;
import dev.projects.sspsoft.model.dto.ApiShipLoginRequestDto;
import dev.projects.sspsoft.model.dto.ApiShipSignupRequestDto;
import dev.projects.sspsoft.model.entity.ApiShipUserEntity;
import dev.projects.sspsoft.model.entity.ApiShipUserTokenEntity;
import dev.projects.sspsoft.repository.AbstractRestRepository;
import dev.projects.sspsoft.repository.apiship.api.ApiShipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Slf4j
@Repository
public class ApiShipRepositoryImpl extends AbstractRestRepository implements ApiShipRepository {

    private final WebClient webClient;

    @Value("${apiship.cities.limit}")
    int citiesLimitRequest;

    public ApiShipRepositoryImpl(@Value("${apiship.url}") String baseUrl,
                                 @Value("${apiship.timeout}") int timeout,
                                 WebClient.Builder webClientBuilder) {
        final HttpClient httpClient = HttpClient.create()
                                                .responseTimeout(Duration.ofSeconds(timeout));

        this.webClient = webClientBuilder.clone()
                                         .clientConnector(new ReactorClientHttpConnector(httpClient))
                                         .baseUrl(baseUrl)
                                         .build();
    }

    @Override
    public ApiShipUserEntity createNewUser(ApiShipSignupRequestDto signupRequestDto) {
        log.info("Выполняется запрос в ApiShip для создания нового пользователя: [{}]", signupRequestDto);
        return sendCommonPublicRequest(webClient, "/users/signup", signupRequestDto, ApiShipUserEntity.class);
    }

    @Override
    public ApiShipUserTokenEntity createSession(ApiShipLoginRequestDto loginRequestDto) {
        log.info("Выполняется запрос в ApiShip для открытия сессии пользователя: [{}]", loginRequestDto.getLogin());
        return sendCommonPublicRequest(webClient, "/users/login", loginRequestDto, ApiShipUserTokenEntity.class);
    }

    //
    @Override
    public ApiShipDefaultProviderCitiesDto getCdekCities(String headerAuthToken) {
        return this.sendCommonPrivateRequest(
                webClient, headerAuthToken, "/lists/providerCities/cdek", ApiShipDefaultProviderCitiesDto.class
        );
    }

    @Override
    protected <R> R sendCommonPrivateRequest(WebClient webClient, String headerAuthToken, String url, Class<R> resultClass) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(url)
                        .queryParam("limit", "{limit}")
                        .build(citiesLimitRequest)
                )
                .header("Authorization", headerAuthToken)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(resultClass)
                .block();
    }
}

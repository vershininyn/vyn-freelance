package dev.projects.sspsoft.repository;

import org.springframework.web.reactive.function.client.WebClient;

public abstract class AbstractRestRepository {

    protected <R> R sendCommonPublicRequest(WebClient webClient, String url, Object body, Class<R> resultClass) {
        return webClient
                .post()
                .uri(url)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(resultClass)
                .block();
    }

    protected <R> R sendCommonPrivateRequest(WebClient webClient, String headerAuthToken, String url, Class<R> resultClass) {
        return webClient
                .get()
                .uri(url)
                .header("Authorization", headerAuthToken)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(resultClass)
                .block();
    }

}

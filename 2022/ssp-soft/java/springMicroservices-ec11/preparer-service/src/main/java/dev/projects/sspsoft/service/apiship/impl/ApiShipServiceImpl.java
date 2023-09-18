package dev.projects.sspsoft.service.apiship.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.projects.sspsoft.model.dto.ApiShipDefaultProviderCitiesDto;
import dev.projects.sspsoft.model.dto.ApiShipLoginRequestDto;
import dev.projects.sspsoft.model.dto.ApiShipSignupRequestDto;
import dev.projects.sspsoft.model.entity.ApiShipUserEntity;
import dev.projects.sspsoft.model.entity.ApiShipUserTokenEntity;
import dev.projects.sspsoft.repository.apiship.api.ApiShipRepository;
import dev.projects.sspsoft.service.apiship.api.ApiShipService;
import dev.projects.sspsoft.service.mq.MessageQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiShipServiceImpl implements ApiShipService {

    private final ApiShipRepository apiShipRepository;
    private final MessageQueue artemis;
    private final ObjectMapper objectMapper;

    public ApiShipServiceImpl(ApiShipRepository apiShipRepository,
                              MessageQueue artemis,
                              ObjectMapper objectMapper) {
        this.apiShipRepository = apiShipRepository;
        this.artemis = artemis;
        this.objectMapper = objectMapper;
    }

    @Override
    public ApiShipUserEntity signup(ApiShipSignupRequestDto signupRequestDto) {
        return apiShipRepository.createNewUser(signupRequestDto);
    }

    @Override
    public ApiShipUserTokenEntity login(ApiShipLoginRequestDto loginRequestDto) {
        return apiShipRepository.createSession(loginRequestDto);
    }

    @Override
    public ApiShipDefaultProviderCitiesDto getCdekCities(String headerAuthToken) {
        final ApiShipDefaultProviderCitiesDto cdekCities = apiShipRepository.getCdekCities(headerAuthToken);

        try {
            final String mqJsonBody = objectMapper.writeValueAsString(cdekCities.getRows());
            artemis.send(mqJsonBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return cdekCities;
    }
}
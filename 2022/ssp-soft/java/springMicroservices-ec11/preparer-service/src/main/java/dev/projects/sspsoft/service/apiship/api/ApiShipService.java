package dev.projects.sspsoft.service.apiship.api;

import dev.projects.sspsoft.model.dto.ApiShipDefaultProviderCitiesDto;
import dev.projects.sspsoft.model.dto.ApiShipLoginRequestDto;
import dev.projects.sspsoft.model.dto.ApiShipSignupRequestDto;
import dev.projects.sspsoft.model.entity.ApiShipUserEntity;
import dev.projects.sspsoft.model.entity.ApiShipUserTokenEntity;

public interface ApiShipService {
    ApiShipUserEntity signup(ApiShipSignupRequestDto signupRequestDto);
    ApiShipUserTokenEntity login(ApiShipLoginRequestDto loginRequestDto);
    ApiShipDefaultProviderCitiesDto getCdekCities(String headerAuthToken);
}
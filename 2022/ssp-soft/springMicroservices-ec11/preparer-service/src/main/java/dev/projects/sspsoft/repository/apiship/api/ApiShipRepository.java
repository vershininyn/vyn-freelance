package dev.projects.sspsoft.repository.apiship.api;

import dev.projects.sspsoft.model.dto.ApiShipDefaultProviderCitiesDto;
import dev.projects.sspsoft.model.dto.ApiShipLoginRequestDto;
import dev.projects.sspsoft.model.dto.ApiShipSignupRequestDto;
import dev.projects.sspsoft.model.entity.ApiShipUserEntity;
import dev.projects.sspsoft.model.entity.ApiShipUserTokenEntity;

public interface ApiShipRepository {

    /**
     * Регистрация пользователя через систему ApiShip
     * @param signupRequestDto
     * @return данные об новом пользователе
     */
    ApiShipUserEntity createNewUser(ApiShipSignupRequestDto signupRequestDto);

    /**
     * Открытие сессии пользователя
     * @param loginRequestDto
     * @return авторизационный токен
     */
    ApiShipUserTokenEntity createSession(ApiShipLoginRequestDto loginRequestDto);

    /**
     * Получение списка городов cdek
     * @return массив городов с мета-информацией
     */
    ApiShipDefaultProviderCitiesDto getCdekCities(String headerAuthToken);
}

package dev.projects.sspsoft.controller;

import dev.projects.sspsoft.model.dto.ApiShipDefaultProviderCitiesDto;
import dev.projects.sspsoft.service.apiship.api.ApiShipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cities", produces = "application/json")
public class CityController {

    private final ApiShipService apiShipService;

    public CityController(ApiShipService apiShipService) {
        this.apiShipService = apiShipService;
    }

    @GetMapping("/cdek")
    public ResponseEntity<ApiShipDefaultProviderCitiesDto> getCdek(@RequestHeader("Authorization") String headerAuthToken) {
        final ApiShipDefaultProviderCitiesDto response = apiShipService.getCdekCities(headerAuthToken);

        return ResponseEntity.ok(response);
    }
}
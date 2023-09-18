package dev.projects.sspsoft.model.dto;

import dev.projects.sspsoft.model.entity.ApiShipCityEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ApiShipDefaultProviderCitiesDto {
    List<ApiShipCityEntity> rows;
    Map<String, String> meta;
}
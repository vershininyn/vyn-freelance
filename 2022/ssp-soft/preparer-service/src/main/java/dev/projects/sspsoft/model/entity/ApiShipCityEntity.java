package dev.projects.sspsoft.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiShipCityEntity {
    private String fiasGuid;
    private String fullName;
    private String cityUuid;
    private String cdekId;
    private String cityName;
    private String oblName;
    private String countryCode;
    private String codCostLimit;
}
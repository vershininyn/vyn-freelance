package dev.projects.sspsoft.apiship.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {
    private String fiasGuid;
    private String fullName;
    private String cityUuid;
    private String cdekId;
    private String cityName;
    private String oblName;
    private String countryCode;
    private Integer codCostLimit;
}

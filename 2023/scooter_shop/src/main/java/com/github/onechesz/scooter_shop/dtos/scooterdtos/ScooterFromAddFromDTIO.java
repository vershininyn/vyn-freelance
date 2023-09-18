package com.github.onechesz.scooter_shop.dtos.scooterdtos;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ScooterFromAddFromDTIO {
    private String scooter_title_dto;
    private BigDecimal scooter_start_price_dto;
    private BigDecimal scooter_start_rent_dto;
    private double scooter_start_charge_dto;
}

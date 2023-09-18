package com.github.onechesz.scooter_shop.dtos.scooterdtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorScooterDTOO {
    private boolean errorIsVisible = false;
    private String cause = "";
}

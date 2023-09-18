package com.github.onechesz.scooter_shop.domain;

import lombok.*;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ScooterShopErrorMessage {
    private String msg;
    private HttpStatus status;
    private DateTime timestamp;
}

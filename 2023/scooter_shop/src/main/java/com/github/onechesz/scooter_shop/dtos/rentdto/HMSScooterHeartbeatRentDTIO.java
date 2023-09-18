package com.github.onechesz.scooter_shop.dtos.rentdto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HMSScooterHeartbeatRentDTIO {
    private List<Integer> scooters_long_ids;
}

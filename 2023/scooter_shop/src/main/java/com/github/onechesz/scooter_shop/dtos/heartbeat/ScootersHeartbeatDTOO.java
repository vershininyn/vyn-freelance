package com.github.onechesz.scooter_shop.dtos.heartbeat;

import com.github.onechesz.scooter_shop.domain.ScooterHeartbeatObject;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ScootersHeartbeatDTOO {
    private Map<Integer, ScooterHeartbeatObject> map;
}

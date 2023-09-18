package com.github.onechesz.scooter_shop.domain;

import com.fasterxml.jackson.datatype.joda.deser.PeriodDeserializer;
import org.springframework.stereotype.Component;

@Component
public class CustomPeriodHeartbeatObjectDeserializer extends PeriodDeserializer {
    public CustomPeriodHeartbeatObjectDeserializer() {
        super(true);
    }
}

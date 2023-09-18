package com.github.onechesz.scooter_shop.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ScooterHeartbeatObject implements java.io.Serializable {
    private DateTime startDateTime;

    @JsonSerialize(using=CustomPeriodHeartbeatObjectSerializer.class)
    @JsonDeserialize(using=CustomPeriodHeartbeatObjectDeserializer.class)
    private Period nowRelativeToStartPeriod;

    private BigDecimal spentMoney;

    private double currentCharge;

    private int userId;

    private double durationAtMinutes;
}

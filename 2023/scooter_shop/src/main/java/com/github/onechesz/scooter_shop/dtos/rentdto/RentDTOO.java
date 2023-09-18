package com.github.onechesz.scooter_shop.dtos.rentdto;

import com.github.onechesz.scooter_shop.entities.ScooterEntity;
import com.github.onechesz.scooter_shop.entities.UserEntity;
import org.jetbrains.annotations.Contract;
import org.joda.time.DateTime;

import java.math.BigDecimal;

public class RentDTOO {
    private UserEntity userEntity;
    private ScooterEntity scooterEntity;
    private long durationAtMinutes;
    private BigDecimal spentMoney;
    private String startedDateAndTimeWithTimeZoneRentString;

    @Contract(pure = true)
    public RentDTOO() {

    }

    @Contract(pure = true)
    public RentDTOO(UserEntity userEntity, ScooterEntity scooterEntity) {
        this.userEntity = userEntity;
        this.scooterEntity = scooterEntity;
    }

    @Contract(pure = true)
    public RentDTOO(UserEntity userEntity,
                    ScooterEntity scooterEntity,
                    long duration,
                    BigDecimal spentMoney,
                    String startedDateAndTimeWithTimeZoneRent) {
        this(userEntity, scooterEntity);

        this.durationAtMinutes = duration;
        this.spentMoney = spentMoney;
        this.startedDateAndTimeWithTimeZoneRentString = startedDateAndTimeWithTimeZoneRentString;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ScooterEntity getScooterEntity() {
        return scooterEntity;
    }

    public void setScooterEntity(ScooterEntity scooterEntity) {
        this.scooterEntity = scooterEntity;
    }

    public long getDurationAtMinutes() {
        return durationAtMinutes;
    }

    public void setDurationAtMinutes(long duration) {
        this.durationAtMinutes = duration;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }

    public String getStartedDateAndTimeWithTimeZoneRentString() {
        return startedDateAndTimeWithTimeZoneRentString;
    }

    public void setStartedDateAndTimeWithTimeZoneRentString(String startedDateAndTimeWithTimeZoneRentString) {
        this.startedDateAndTimeWithTimeZoneRentString = startedDateAndTimeWithTimeZoneRentString;
    }
}

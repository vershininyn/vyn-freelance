package com.github.onechesz.scooter_shop.entities;

import com.github.onechesz.scooter_shop.dtos.rentdto.RentDTOO;
import com.github.onechesz.scooter_shop.utils.JodaDateTimeWithTimeZoneUtil;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;

@Entity
@Table(name = "rent")
public class RentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int scooterId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @OneToOne
    @JoinColumn(name = "scooter_id")
    private ScooterEntity scooterEntity;
    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Contract(pure = true)
    public RentEntity() {

    }

    @Contract(pure = true)
    public RentEntity(UserEntity userEntity, ScooterEntity scooterEntity, DateTime startTime) {
        this.userEntity = userEntity;
        this.scooterEntity = scooterEntity;

        setStartTime(startTime);
//        this.startTime = new Timestamp(startTime.toDateTime().getMillis());
    }

    @Contract(pure = true)
    public RentEntity(int scooterId, UserEntity userEntity, ScooterEntity scooterEntity, DateTime startTime) {
        this(userEntity, scooterEntity, startTime);
        this.scooterId = scooterId;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull RentDTOO convertToRentDTOO(@NotNull RentEntity rentEntity) {
        return new RentDTOO(rentEntity.userEntity, rentEntity.scooterEntity);
    }

    public int getScooterId() {
        return scooterId;
    }

    public void setScooterId(int scooterId) {
        this.scooterId = scooterId;
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

    public DateTime getStartTime(JodaDateTimeWithTimeZoneUtil jodaDateTimeUtil) {
        return jodaDateTimeUtil.toDateTimeFromEpochMilli(startTime.toInstant().toEpochMilli());
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime.toGregorianCalendar().toZonedDateTime();
    }
}

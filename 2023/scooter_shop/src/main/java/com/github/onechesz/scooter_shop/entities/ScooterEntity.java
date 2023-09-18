package com.github.onechesz.scooter_shop.entities;

import com.github.onechesz.scooter_shop.dtos.scooterdtos.ScooterDTOO;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "scooter")
public class ScooterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "charge")
    private double charge;
    @Column(name = "start_price")
    private BigDecimal startPrice;
    @Column(name = "rent_price")
    private BigDecimal rentPrice;
    @Column(name = "is_already_under_rent")
    private boolean isAlreadyUnderRent;
    @OneToOne(mappedBy = "scooterEntity", cascade = CascadeType.ALL)
    private RentEntity rentEntity;

    @Contract(pure = true)
    public ScooterEntity() {

    }

    @Contract(pure = true)
    public ScooterEntity(String title, double charge, BigDecimal startPrice, BigDecimal rentPrice, boolean isAlreadyUnderRent) {
        this.title = title;
        this.charge = charge;
        this.startPrice = startPrice;
        this.rentPrice = rentPrice;
        this.isAlreadyUnderRent = isAlreadyUnderRent;
    }

    @Contract(pure = true)
    public ScooterEntity(int id, String title, double charge, BigDecimal startPrice, BigDecimal rentPrice, boolean isAlreadyUnderRent, RentEntity rentEntity) {
        this.id = id;
        this.title = title;
        this.charge = charge;
        this.startPrice = startPrice;
        this.rentPrice = rentPrice;
        this.rentEntity = rentEntity;
        this.isAlreadyUnderRent = isAlreadyUnderRent;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull ScooterDTOO convertToScooterDTOO(@NotNull ScooterEntity scooterEntity) {
        return new ScooterDTOO(scooterEntity.id,
                scooterEntity.title,
                scooterEntity.charge,
                scooterEntity.startPrice,
                scooterEntity.rentPrice);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public RentEntity getRentEntity() {
        return rentEntity;
    }

    public void setRentEntity(RentEntity rentEntity) {
        this.rentEntity = rentEntity;
    }

    public boolean getAlreadyUnderRent() {
        return isAlreadyUnderRent;
    }

    public void setAlreadyUnderRent(boolean isAlreadyUnderRent) {
        this.isAlreadyUnderRent = isAlreadyUnderRent;
    }
}

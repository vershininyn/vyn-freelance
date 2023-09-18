package com.github.onechesz.scooter_shop.dtos.scooterdtos;

import org.jetbrains.annotations.Contract;

import java.math.BigDecimal;

public class ScooterDTIO {
    private String title;
    private BigDecimal startPrice;
    private BigDecimal rentPrice;
    private double charge;

    @Contract(pure = true)
    public ScooterDTIO() {

    }

    @Contract(pure = true)
    public ScooterDTIO(String title, BigDecimal startPrice, BigDecimal rentPrice, double charge) {
        this.title = title;
        this.startPrice = startPrice;
        this.rentPrice = rentPrice;
        this.charge = charge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
}

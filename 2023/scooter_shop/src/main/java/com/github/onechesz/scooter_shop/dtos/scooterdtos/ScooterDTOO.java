package com.github.onechesz.scooter_shop.dtos.scooterdtos;

import org.jetbrains.annotations.Contract;

import java.math.BigDecimal;

public class ScooterDTOO {
    private int id;
    private String title;
    private double charge;
    private BigDecimal startPrice;
    private BigDecimal rentPrice;

    @Contract(pure = true)
    public ScooterDTOO() {

    }

    @Contract(pure = true)
    public ScooterDTOO(int id, String title, double charge, BigDecimal startPrice, BigDecimal rentPrice) {
        this.id = id;
        this.title = title;
        this.charge = charge;
        this.startPrice = startPrice;
        this.rentPrice = rentPrice;
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
}

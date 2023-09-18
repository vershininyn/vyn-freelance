package com.github.onechesz.scooter_shop.dtos.userdtos;

import org.jetbrains.annotations.Contract;

import java.math.BigDecimal;

public class UserDTOO {
    private int id;
    private String name;
    private String role;
    private BigDecimal balance;

    @Contract(pure = true)
    public UserDTOO() {

    }

    @Contract(pure = true)
    public UserDTOO(int id, String name, String role, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

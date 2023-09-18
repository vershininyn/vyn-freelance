package com.github.onechesz.scooter_shop.entities;

import com.github.onechesz.scooter_shop.dtos.userdtos.UserDTOO;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "balance")
    private BigDecimal balance;
    @OneToMany(mappedBy = "userEntity")
    private List<RentEntity> rentEntities;

    @Contract(pure = true)
    public UserEntity() {

    }

    @Contract(pure = true)
    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Contract(pure = true)
    public UserEntity(String name, String password, String role, BigDecimal balance) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.balance = balance;
    }

    @Contract(pure = true)
    public UserEntity(int id, String name, String password, String role, BigDecimal balance, List<RentEntity> rentEntities) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.balance = balance;
        this.rentEntities = rentEntities;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull UserDTOO convertToUserDTOO(@NotNull UserEntity userEntity) {
        return new UserDTOO(userEntity.id, userEntity.name, userEntity.role, userEntity.balance);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<RentEntity> getRentEntities() {
        return rentEntities;
    }

    public void setRentEntities(List<RentEntity> rentEntities) {
        this.rentEntities = rentEntities;
    }
}

package com.github.onechesz.scooter_shop.dtos.userdtos;

import com.github.onechesz.scooter_shop.entities.UserEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class UserDTIO {
    private String name;
    private String password;
    private String passwordConfirmation;

    @Contract(pure = true)
    public UserDTIO() {

    }

    @Contract(pure = true)
    public UserDTIO(String name, String password, String passwordConfirmation) {
        this.name = name;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull UserEntity convertToUserEntity(@NotNull UserDTIO userDTIO) {
        return new UserEntity(userDTIO.name, userDTIO.password);
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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}

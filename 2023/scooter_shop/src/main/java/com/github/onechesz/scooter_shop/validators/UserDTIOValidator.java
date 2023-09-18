package com.github.onechesz.scooter_shop.validators;

import com.github.onechesz.scooter_shop.dtos.userdtos.UserDTIO;
import com.github.onechesz.scooter_shop.services.UserService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserDTIOValidator implements Validator {
    private final UserService userService;

    @Contract(pure = true)
    public UserDTIOValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserDTIO.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        UserDTIO userDTIO = (UserDTIO) target;

        if (!userDTIO.getPassword().equals(userDTIO.getPasswordConfirmation()))
            errors.rejectValue("passwordConfirmation", "", "Пароли не совпадают.");

        if (userService.findByName(userDTIO.getName()).isPresent())
            errors.rejectValue("name", "", "Пользователь с таким именем уже существует.");
    }
}

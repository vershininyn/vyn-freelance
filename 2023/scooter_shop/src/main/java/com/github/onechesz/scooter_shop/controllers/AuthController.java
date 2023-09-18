package com.github.onechesz.scooter_shop.controllers;

import com.github.onechesz.scooter_shop.dtos.userdtos.UserDTIO;
import com.github.onechesz.scooter_shop.services.UserService;
import com.github.onechesz.scooter_shop.validators.UserDTIOValidator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "")
public class AuthController {
    private final UserDTIOValidator userDTIOValidator;
    private final UserService userService;

    @Contract(pure = true)
    public AuthController(UserDTIOValidator userDTIOValidator, UserService userService) {
        this.userDTIOValidator = userDTIOValidator;
        this.userService = userService;
    }

    @GetMapping(path = "/login")
    public String loginView() {
        return "auth/login";
    }

    @GetMapping(path = "/register")
    public String registerView(@NotNull Model model) {
        model.addAttribute("user_dtio", new UserDTIO());

        return "auth/register";
    }

    @PostMapping(path = "/register")
    public String registerProcess(@ModelAttribute(name = "user_dtio") UserDTIO userDTIO, BindingResult bindingResult) {
//        userDTIOValidator.validateFields(userDTIO, bindingResult);
//
//        if (bindingResult.hasErrors())
//            return "auth/register";

        userDTIOValidator.validate(userDTIO, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/register";

        userService.save(userDTIO);

        return "redirect:/login";
    }
}

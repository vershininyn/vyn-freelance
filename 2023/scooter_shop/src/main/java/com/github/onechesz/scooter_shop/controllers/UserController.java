package com.github.onechesz.scooter_shop.controllers;

import com.github.onechesz.scooter_shop.dtos.userdtos.UserDTOO;
import com.github.onechesz.scooter_shop.services.UserService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @Contract(pure = true)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "")
    public String usersView(@NotNull Model model) {
        model.addAttribute("user_dtoos", userService.findAll());

        return "users/users";
    }

    @GetMapping(path = "/{id}")
    public String userView(@PathVariable(name = "id") int id, Model model) {
        Optional<UserDTOO> userDTOOOptional = userService.findById(id);

        if (userDTOOOptional.isPresent()) {
            model.addAttribute("user_dtoo", userDTOOOptional.get());

            return "users/user";
        }

        return "redirect:/users";
    }

    @PatchMapping(path = "/{id}/edit")
    public String userRoleEditProcessing(@PathVariable(name = "id") int id, @RequestParam(name = "user_role") String role) {
        userService.editRole(id, role);

        return "redirect:/users/" + id;
    }
}

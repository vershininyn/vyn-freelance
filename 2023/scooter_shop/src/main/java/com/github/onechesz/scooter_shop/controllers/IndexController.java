package com.github.onechesz.scooter_shop.controllers;

import com.github.onechesz.scooter_shop.services.ScooterService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "")
public class IndexController {
    private final ScooterService scooterService;

    @Contract(pure = true)
    public IndexController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @GetMapping(path = "")
    public String indexView(@NotNull Model model) {
        model.addAttribute("scooter_dtoos", scooterService.findAllExceptAlreadyUnderRent());

        return "index/index";
    }
}
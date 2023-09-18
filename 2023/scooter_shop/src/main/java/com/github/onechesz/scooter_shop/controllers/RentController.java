package com.github.onechesz.scooter_shop.controllers;

import com.github.onechesz.scooter_shop.security.UserDetailsImpl;
import com.github.onechesz.scooter_shop.services.RentService;
import com.github.onechesz.scooter_shop.services.UserService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping(path = "/rents")
public class RentController {
    private final RentService rentService;
    private final UserService userService;

    @Contract(pure = true)
    public RentController(RentService rentService, UserService userService) {
        this.rentService = rentService;
        this.userService = userService;
    }

    @GetMapping(path = "")
    public String userRentsView(@NotNull Model model) {
        int userId = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId();

        model.addAttribute("rent_dtoos", rentService.findByUserId((userId)));
        userService.findById(userId).ifPresent(userDTOO -> model.addAttribute("user_dtoo", userDTOO));

        return "rents/user_rents";
    }

    @PostMapping(path = "/{scooter_id}/stop")
    public String stopProcessing(@PathVariable(name = "scooter_id") int scooterId,
                                 @RequestParam(name = "user_id") int userId,
                                 @RequestParam(name = "rent_duration") long rentDuration) {
        rentService.stop(userId, scooterId, rentDuration);

        return "redirect:/rents";
    }

    @PostMapping(path = "/{user_id}/replenish")
    public String replenishBalanceProcessing(@PathVariable(name = "user_id") int userId, @RequestParam(name = "decimal") BigDecimal sum) {
        userService.replenishBalance(userId, sum);

        return "redirect:/rents";
    }
}

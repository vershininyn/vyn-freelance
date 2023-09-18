package com.github.onechesz.scooter_shop.controllers;

import com.github.onechesz.scooter_shop.dtos.scooterdtos.ErrorScooterDTOO;
import com.github.onechesz.scooter_shop.dtos.scooterdtos.ScooterDTIO;
import com.github.onechesz.scooter_shop.dtos.scooterdtos.ScooterDTOO;
import com.github.onechesz.scooter_shop.security.UserDetailsImpl;
import com.github.onechesz.scooter_shop.services.RentService;
import com.github.onechesz.scooter_shop.services.ScooterService;
import com.github.onechesz.scooter_shop.validators.ScooterDTIOValidator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(path = "/scooters")
public class ScooterController {
    private final ScooterService scooterService;
    private final RentService rentService;
    private final ScooterDTIOValidator scooterDTIOValidator;

    @Contract(pure = true)
    public ScooterController(ScooterService scooterService,
                             RentService rentService,
                             ScooterDTIOValidator scooterDTIOValidator) {
        this.scooterService = scooterService;
        this.rentService = rentService;
        this.scooterDTIOValidator = scooterDTIOValidator;
    }

    @GetMapping(path = "/{id}")
    public String scooterView(@PathVariable(name = "id") int id, @NotNull Model model) {
        scooterService.findById(id).ifPresent(scooterDTOO -> {
            model.addAttribute("scooter_dtoo", scooterDTOO);
            rentService.findByScooterId(id).ifPresent(rentEntity -> model.addAttribute("is_rented", true));
        });

        return "scooters/scooter";
    }

    @PostMapping(path = "/{id}/start_rent")
    public String startRentProcessing(@PathVariable(name = "id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            if (!rentService.start(((UserDetailsImpl) authentication.getPrincipal()).getId(), id)) {
                model.addAttribute("no_money", true);

                return scooterView(id, model);
            }

            return "redirect:/scooters/" + id;
        }

        return "redirect:/login";
    }

    @GetMapping(path = "/{id}/edit")
    public String editScooterParametersView(@PathVariable(name = "id") int id, @NotNull Model model) {
        Optional<ScooterDTOO> scooterDTOOOptional = scooterService.findById(id);

        if (scooterDTOOOptional.isPresent()) {
            model.addAttribute("scooter_dtoo", scooterDTOOOptional.get());
            model.addAttribute("scooter_dtio", new ScooterDTIO());

            return "scooters/edit";
        }

        return "redirect:/";
    }

    @PatchMapping(path = "/{id}/edit")
    public String editScooterParametersProcessing(@PathVariable(name = "id") int id,
                                                  @ModelAttribute(name = "scooter_dtio") ScooterDTIO scooterDTIO,
                                                  @NotNull Model model,
                                                  BindingResult bindingResult) {
        Objects.requireNonNull(scooterDTIO);
        Objects.requireNonNull(bindingResult);

        model.addAttribute("is_errors_exists", false);

        if (scooterDTIO.getTitle().isBlank()) {
            model.addAttribute("is_errors_exists", true);
            return editScooterParametersView(id, model);
        }

        scooterService.editScooterParameters(id, scooterDTIO);

        return "redirect:/scooters/" + id;
    }

    @DeleteMapping(path = "/{id}/delete")
    public String deleteScooterProcessing(@PathVariable(name = "id") int id) {
        scooterService.deleteScooter(id);

        return "redirect:/";
    }

    @GetMapping(path = "/add")
    public String addScooterView(@NotNull Model model) {
//        model.addAttribute(new ScooterDTIO());
        model.addAttribute("error_scooter_dtoo", new ErrorScooterDTOO());

        return "scooters/add";
    }

    @PostMapping(path = "/add")
    public String addScooterProcessing(@ModelAttribute(name = "scooter_dtio") ScooterDTIO scooterDTIO,
                                       @NotNull Model model,
                                       BindingResult bindingResult) {
        Objects.requireNonNull(scooterDTIO);
        Objects.requireNonNull(bindingResult);
        model.addAttribute("is_errors_exists", false);

        scooterDTIOValidator.validate(scooterDTIO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("is_errors_exists", true);
            return "scooters/add";
        }

        scooterService.add(scooterDTIO);

        return "redirect:/";
    }
}

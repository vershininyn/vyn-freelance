package com.github.onechesz.scooter_shop.config;

import com.github.onechesz.scooter_shop.domain.ScooterShopErrorMessage;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
public class ScooterShopAdvice {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleException(@NotNull Exception ex, WebRequest request) {
        ScooterShopErrorMessage error = ScooterShopErrorMessage.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .msg(ex.getLocalizedMessage())
                .timestamp(DateTime.now())
                .build();

        return new ModelAndView("errors/error",
                Map.of("scooter_error_obj", error),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


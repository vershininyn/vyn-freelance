package com.github.onechesz.scooter_shop.validators;

import com.github.onechesz.scooter_shop.dtos.scooterdtos.ScooterDTIO;
import com.github.onechesz.scooter_shop.dtos.userdtos.UserDTIO;
import com.github.onechesz.scooter_shop.services.ScooterService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.StringJoiner;

@Component
public class ScooterDTIOValidator implements Validator {
    private ScooterService scooterService;

    @Contract(pure = true)
    public ScooterDTIOValidator(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return ScooterDTIO.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        ScooterDTIO scooterDTIO = (ScooterDTIO) (target);

        String title = scooterDTIO.getTitle();

        if (scooterService.isScooterAlreadyExistsByTitle(title)) {
            errors.rejectValue("title",
                    "",
                    "Скутер с таким названием уже существует.");
        }

       /*checkBigDecimal(scooterDTIO.getStartPrice(),
                errors,
                "startPrice",
                " начальной цены ",
                0,
                5,
                0,
                3);

        checkBigDecimal(scooterDTIO.getRentPrice(),
                errors,
                "rentPrice",
                " цены поминутной аренды ",
                0,
                5,
                0,
                3);

        double  charge = scooterDTIO.getCharge();

        if (charge <= 0.0) {
            errors.rejectValue("charge", "", "Заряд не может быть отрицательным");
        }*/
    }

    private void checkBigDecimal(@NotNull BigDecimal decimal,
                                 Errors errors,
                                 String errorType,
                                 String decimalType,
                                 int leftExclusivePrecisionValue,
                                 int rightExclusivePrecisionValue,
                                 int leftExclusiveScaleValue,
                                 int rightExclusiveScaleValue) {

        int precision = decimal.precision();

        if (!(precision > leftExclusivePrecisionValue && precision < rightExclusivePrecisionValue)) {
            errors.rejectValue(errorType,
                    "",
                    getCheckProcessString(" целой ", decimalType, leftExclusivePrecisionValue, rightExclusivePrecisionValue));
        }

        int scale = decimal.scale();

        if (!(scale > leftExclusiveScaleValue && scale < rightExclusiveScaleValue)) {
            errors.rejectValue(errorType,
                    "",
                    getCheckProcessString(" дробной ", decimalType, leftExclusiveScaleValue, rightExclusiveScaleValue));
        }
    }

    private String getCheckProcessString(String decimalPartType, String processMessage, int leftValue, int rightValue) {
        return (new StringJoiner("", "", ""))
                .add("Число знаков ")
                .add(decimalPartType)
                .add(" части ")
                .add(processMessage)
                .add(" должно принадлежать интервалу [")
                .add(String.valueOf(leftValue + 1))
                .add(",")
                .add(String.valueOf(rightValue + 1))
                .add("]")
                .toString();
    }

}
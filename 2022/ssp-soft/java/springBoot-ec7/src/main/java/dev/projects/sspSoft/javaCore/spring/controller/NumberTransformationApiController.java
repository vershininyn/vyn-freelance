package dev.projects.sspSoft.javaCore.spring.controller;

import dev.projects.sspSoft.javaCore.spring.model.numberTransformation.NumberTransformRequestDTO;
import dev.projects.sspSoft.javaCore.spring.model.numberTransformation.NumberTransformResponseDTO;
import dev.projects.sspSoft.javaCore.spring.service.NumberTransformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(
        produces = "application/json")
public class NumberTransformationApiController {
    @Autowired
    private NumberTransformationService numberService;

    @Tag(name = "Контроллер преобразования чисел", description = "Преобразует число в строку переданное через тело")
    @Operation(summary = "Метод преобразования числа в его строковое представление через тело")
    @PostMapping(value = "/transform")
    public ResponseEntity<NumberTransformResponseDTO> transformByPost(@RequestBody @Valid NumberTransformRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(getByTransform(dto.getNumber()));
    }

    @Tag(name = "Контроллер преобразования чисел", description = "Преобразует число в строку переданное через строку запроса")
    @Operation(summary = "Метод преобразования числа в его строковое представление через переменную")
    @GetMapping(value = "/transform/{number}")
    public ResponseEntity<NumberTransformResponseDTO> transformByGet(@PathVariable String number) {
        return ResponseEntity.ok(getByTransform(number));
    }

    private NumberTransformResponseDTO getByTransform(String number) {
        final String numberInWord = numberService.transformNumber(number);

        return new NumberTransformResponseDTO(numberInWord);
    }
}

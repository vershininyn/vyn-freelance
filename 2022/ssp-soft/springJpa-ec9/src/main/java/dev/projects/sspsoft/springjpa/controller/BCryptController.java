package dev.projects.sspsoft.springjpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        produces = "application/json")
public class BCryptController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Tag(name = "Контроллер работы с bcrypt", description = "Генерация хэша")
    @Operation(summary = "Контроллер работы с bcrypt")
    @GetMapping(value = "/bcrypt")
    public ResponseEntity<String> generatePassword(@RequestParam("string") String string) {
        String encoded = passwordEncoder.encode(string);

        return ResponseEntity.ok(encoded);
    }
}

package dev.projects.sspsoft.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiShipLoginRequestDto {
    private String login;
    private String password;
}
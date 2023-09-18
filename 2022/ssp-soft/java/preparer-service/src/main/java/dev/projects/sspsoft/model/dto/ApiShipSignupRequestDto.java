package dev.projects.sspsoft.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiShipSignupRequestDto {
    private String login;
    private String email;
    private String password;
}

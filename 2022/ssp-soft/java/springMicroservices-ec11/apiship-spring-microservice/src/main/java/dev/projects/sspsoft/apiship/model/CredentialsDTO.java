package dev.projects.sspsoft.apiship.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CredentialsDTO {
    private String login;
    private String password;
}

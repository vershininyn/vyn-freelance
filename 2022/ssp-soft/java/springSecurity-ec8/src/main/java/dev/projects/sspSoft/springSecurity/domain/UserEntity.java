package dev.projects.sspSoft.springSecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    private String login;
    private String password;
    private String email;
    private List<UserRoleEntity> roles;
    private LocalDateTime dateAndTimeOfRegistered;
}

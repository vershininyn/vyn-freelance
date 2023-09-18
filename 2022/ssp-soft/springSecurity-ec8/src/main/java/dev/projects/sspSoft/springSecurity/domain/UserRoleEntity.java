package dev.projects.sspSoft.springSecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
@Setter
public class UserRoleEntity implements GrantedAuthority {
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}
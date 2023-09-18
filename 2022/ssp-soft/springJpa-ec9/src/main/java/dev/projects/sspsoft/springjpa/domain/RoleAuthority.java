package dev.projects.sspsoft.springjpa.domain;

import org.springframework.security.core.GrantedAuthority;

public class RoleAuthority implements GrantedAuthority {
    private RoleEntity role;

    public RoleAuthority(RoleEntity role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getName();
    }
}

package by.babanin.newsportalrest.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMINISTRATOR;

    private final String role = "ROLE_" + name();

    @Override
    public String getAuthority() {
        return role;
    }
}

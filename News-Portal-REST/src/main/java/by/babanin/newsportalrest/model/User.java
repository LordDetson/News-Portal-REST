package by.babanin.newsportalrest.model;

import by.babanin.newsportalrest.model.view.ViewUser;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewUser.NoPassword.class)
    private Integer id;
    @JsonView(ViewUser.NoPassword.class)
    private String username;
    @JsonView(ViewUser.FullUser.class)
    private String password;
    @JsonView(ViewUser.NoPassword.class)
    private Boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @JsonView(ViewUser.NoPassword.class)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getActive();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public static UserBuilder builder(PasswordEncoder passwordEncoder) {
        return new User().new UserBuilder(passwordEncoder);
    }

    public class UserBuilder {
        private final PasswordEncoder passwordEncoder;

        private UserBuilder(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
            User.this.active = Boolean.TRUE;
        }

        public UserBuilder setUsername(String username) {
            User.this.username = username;
            return this;
        }

        public UserBuilder setPassword(String password) {
            User.this.password = passwordEncoder.encode(password);
            return this;
        }

        public UserBuilder setActive(Boolean active) {
            User.this.active = active;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}

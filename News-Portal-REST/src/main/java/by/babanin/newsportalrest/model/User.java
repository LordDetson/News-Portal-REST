package by.babanin.newsportalrest.model;

import by.babanin.newsportalrest.model.view.ViewUser;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewUser.NoPassword.class)
    private Integer id;

    @Column(length = 50, nullable = false)
    @Size(min = 2, max = 50)
    @JsonView(ViewUser.NoPassword.class)
    private @NotEmpty String username;

    @Column(length = 100, nullable = false)
    @Size(min = 2, max = 100)
    @JsonView(ViewUser.FullUser.class)
    private @NotEmpty String password;

    @Column(nullable = false)
    @JsonView(ViewUser.NoPassword.class)
    private @NotNull Boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @JsonView(ViewUser.NoPassword.class)
    private @NotEmpty Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<NewsItem> publications;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
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

        public UserBuilder setRoles(Set<Role> roles) {
            User.this.roles = roles;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}

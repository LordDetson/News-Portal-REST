package by.babanin.newsportalrest.service.impl;

import by.babanin.newsportalrest.dao.UserRepository;
import by.babanin.newsportalrest.model.Role;
import by.babanin.newsportalrest.model.User;
import by.babanin.newsportalrest.service.UserService;
import by.babanin.newsportalrest.service.exception.UserExistsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUser(String username) {
        return (User) loadUserByUsername(username);
    }

    @Override
    public User addUser(User user) {
        if (!Objects.isNull(getUser(user.getUsername())))
            throw new UserExistsException("Порльзователь с username " + user.getUsername() + " существует");
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(Boolean.TRUE);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }
}

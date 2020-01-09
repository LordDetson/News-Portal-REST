package by.babanin.newsportalrest.config;

import by.babanin.newsportalrest.dao.UserRepository;
import by.babanin.newsportalrest.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class InitDatabaseConfig {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${initdb.user.username}")
    private String userUsername;
    @Value("${initdb.user.password}")
    private String userPassword;
    @Value("${initdb.admin.username}")
    private String adminUsername;
    @Value("${initdb.admin.password}")
    private String adminPassword;

    public InitDatabaseConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            userRepository.deleteAll();

            User user = User.builder(passwordEncoder)
                    .setUsername(userUsername)
                    .setPassword(userPassword)
                    .build();

            User admin = User.builder(passwordEncoder)
                    .setUsername(adminUsername)
                    .setPassword(adminPassword)
                    .build();

            userRepository.save(user);
            userRepository.save(admin);

            System.out.println("----- Profiles ------");
            List<User> users = userRepository.findAll();
            users.forEach(System.out::println);
        };
    }
}

package by.babanin.newsportalrest.config;

import by.babanin.newsportalrest.dao.NewsItemRepository;
import by.babanin.newsportalrest.dao.UserRepository;
import by.babanin.newsportalrest.model.NewsItem;
import by.babanin.newsportalrest.model.Role;
import by.babanin.newsportalrest.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Configuration
public class InitDatabaseConfig {
    private final UserRepository userRepository;
    private final NewsItemRepository newsItemRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${initdb.user.username}")
    private String userUsername;
    @Value("${initdb.user.password}")
    private String userPassword;
    @Value("${initdb.admin.username}")
    private String adminUsername;
    @Value("${initdb.admin.password}")
    private String adminPassword;

    public InitDatabaseConfig(UserRepository userRepository, NewsItemRepository newsItemRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.newsItemRepository = newsItemRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            userRepository.deleteAll();
            newsItemRepository.deleteAll();

            User user = User.builder(passwordEncoder)
                    .setUsername(userUsername)
                    .setPassword(userPassword)
                    .setRoles(Collections.singleton(Role.USER))
                    .build();

            User admin = User.builder(passwordEncoder)
                    .setUsername(adminUsername)
                    .setPassword(adminPassword)
                    .setRoles(new HashSet<>(Arrays.asList(Role.USER, Role.ADMINISTRATOR)))
                    .build();

            userRepository.save(user);
            userRepository.save(admin);

            User adminFromDB = userRepository.findByUsername("admin");

            NewsItem newsItem = new NewsItem();
            newsItem.setTitle("Test news");
            newsItem.setContent("New news");
            newsItem.setPublicationData(LocalDateTime.of(2020, Month.APRIL, 1, 1, 0));
            newsItem.setAuthor(adminFromDB);
            newsItemRepository.save(newsItem);

            System.out.println("----- Profiles ------");
            List<User> users = userRepository.findAll();
            users.forEach(System.out::println);
            System.out.println("----- News ------");
            List<NewsItem> news = newsItemRepository.findAll();
            news.forEach(System.out::println);
        };
    }
}

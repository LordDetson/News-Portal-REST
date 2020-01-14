package by.babanin.newsportalrest.config;

import by.babanin.newsportalrest.dao.CommentItemRepository;
import by.babanin.newsportalrest.dao.NewsItemRepository;
import by.babanin.newsportalrest.dao.UserRepository;
import by.babanin.newsportalrest.model.CommentItem;
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
    private final CommentItemRepository commentItemRepository;

    @Value("${initdb.user.username}")
    private String userUsername;
    @Value("${initdb.user.password}")
    private String userPassword;
    @Value("${initdb.admin.username}")
    private String adminUsername;
    @Value("${initdb.admin.password}")
    private String adminPassword;

    public InitDatabaseConfig(UserRepository userRepository, NewsItemRepository newsItemRepository, PasswordEncoder passwordEncoder, CommentItemRepository commentItemRepository) {
        this.userRepository = userRepository;
        this.newsItemRepository = newsItemRepository;
        this.passwordEncoder = passwordEncoder;
        this.commentItemRepository = commentItemRepository;
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            userRepository.deleteAll();
            newsItemRepository.deleteAll();

            User user = User.builder()
                    .username(userUsername)
                    .password(passwordEncoder.encode(userPassword))
                    .roles(Collections.singleton(Role.USER))
                    .active(true)
                    .accountNonLocked(false)
                    .build();

            User admin = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .roles(new HashSet<>(Arrays.asList(Role.USER, Role.ADMINISTRATOR)))
                    .active(true)
                    .accountNonLocked(true)
                    .build();

            userRepository.save(user);
            userRepository.save(admin);

            User userFromDB = userRepository.findByUsername("user");
            User adminFromDB = userRepository.findByUsername("admin");

            NewsItem newsItem = NewsItem.builder()
                    .title("Test news")
                    .content("New news")
                    .publicationData(LocalDateTime.of(2020, Month.APRIL, 1, 1, 0))
                    .author(adminFromDB)
                    .build();

            NewsItem newsItemFromDB = newsItemRepository.save(newsItem);

            CommentItem commentItem = CommentItem.builder()
                    .author(userFromDB)
                    .publication(newsItemFromDB)
                    .commentDate(LocalDateTime.of(2020, Month.APRIL, 1, 2, 0))
                    .message("It's cool!")
                    .build();

            commentItemRepository.save(commentItem);

            System.out.println("----- Profiles ------");
            List<User> users = userRepository.findAll();
            users.forEach(System.out::println);
            System.out.println("----- News ------");
            List<NewsItem> news = newsItemRepository.findAll();
            news.forEach(System.out::println);
            System.out.println("----- Comments ------");
            List<CommentItem> comments = commentItemRepository.findAll();
            comments.forEach(System.out::println);
        };
    }
}

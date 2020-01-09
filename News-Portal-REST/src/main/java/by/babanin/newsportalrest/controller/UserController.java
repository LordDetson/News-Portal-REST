package by.babanin.newsportalrest.controller;

import by.babanin.newsportalrest.model.User;
import by.babanin.newsportalrest.model.view.ViewUser;
import by.babanin.newsportalrest.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    @JsonView(ViewUser.NoPassword.class)
    public User registration(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userService.addUser(user);
    }
}

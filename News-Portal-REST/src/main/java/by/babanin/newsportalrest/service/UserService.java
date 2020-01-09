package by.babanin.newsportalrest.service;

import by.babanin.newsportalrest.model.User;

public interface UserService {
    User getUser(String username);

    User addUser(User user);
}

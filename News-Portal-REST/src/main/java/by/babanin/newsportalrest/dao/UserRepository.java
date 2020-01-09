package by.babanin.newsportalrest.dao;

import by.babanin.newsportalrest.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAll();

    User findByUsername(String username);
}

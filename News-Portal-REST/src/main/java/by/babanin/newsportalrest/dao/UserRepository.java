package by.babanin.newsportalrest.dao;

import by.babanin.newsportalrest.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAll();

    Optional<User> findByUsername(String username);
}

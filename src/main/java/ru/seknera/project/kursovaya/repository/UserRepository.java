package ru.seknera.project.kursovaya.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.seknera.project.kursovaya.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

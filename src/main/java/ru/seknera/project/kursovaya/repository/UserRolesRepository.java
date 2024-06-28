package ru.seknera.project.kursovaya.repository;

import org.springframework.data.repository.CrudRepository;
import ru.seknera.project.kursovaya.model.UserRole;

public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
}
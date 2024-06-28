package ru.seknera.project.kursovaya.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.seknera.project.kursovaya.model.UserAuthority;
import ru.seknera.project.kursovaya.model.UserRole;

import java.util.List;

public interface UserService {

    void registration(String username, String password);
    UserDetails loadUserByUsername(String username);
    List<UserRole> getUserRoles(String username);
    void addRoleToUser(String username, UserAuthority role);
    void removeRoleFromUser(String username, UserAuthority role);
}

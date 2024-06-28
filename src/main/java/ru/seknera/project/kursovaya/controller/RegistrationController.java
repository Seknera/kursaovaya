package ru.seknera.project.kursovaya.controller;

import ru.seknera.project.kursovaya.model.UserAuthority;
import ru.seknera.project.kursovaya.model.UserRole;
import ru.seknera.project.kursovaya.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> registration(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    )
    {
        userService.registration(username, password);
        return ResponseEntity.ok().build();
    }
}



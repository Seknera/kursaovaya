package ru.seknera.project.kursovaya.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.seknera.project.kursovaya.model.UserAuthority;
import ru.seknera.project.kursovaya.model.UserRole;
import ru.seknera.project.kursovaya.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final UserService userService;

    public RoleController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addRole(
            @RequestParam("userName") String userName,
            @RequestParam("role") UserAuthority authority) {
        userService.addRoleToUser(userName, authority);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeRole(
            @RequestParam("userName") String userName,
            @RequestParam("role") UserAuthority authority) {
        userService.removeRoleFromUser(userName, authority);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserRole>> getUserRoles(@PathVariable String userName) {
        List<UserRole> roles = userService.getUserRoles(userName);
        return ResponseEntity.ok(roles);
    }
}

package ru.kata.spring.boot_security.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public List<User> getAdminPage(Model model, Authentication authentication) {
        List<User> users = userService.listUsers();
        return users;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<String> addUser(
            @RequestBody User user) {
        this.userService.create(user);
        return ResponseEntity.ok("fine");
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        user.setId(id);
        userService.update(user);
        return ResponseEntity.ok("fine");
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {
        userService.delete(userService.get(id));
        return ResponseEntity.ok("fine");
    }
}

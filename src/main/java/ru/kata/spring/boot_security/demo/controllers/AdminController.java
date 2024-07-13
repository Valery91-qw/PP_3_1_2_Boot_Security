package ru.kata.spring.boot_security.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> getAdminPage(Model model, Authentication authentication) {
        List<User> users = userService.listUsers();
        return users;
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, @ModelAttribute("role") Role role) {
        String username = extractUserName(user.getName());
        List<Role> roles = exstractRoles(role.getName());
        user.setName(username);
        user.setRoles(roles);
        this.userService.create(user);
        return "redirect:/";
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

    private List<Role> exstractRoles(String names) {
        String[] allNames = names.split(",");
        List<Role> roles = new ArrayList<>();
        for (String name : allNames) {
            if (name.contains("ROLE_")) {
                roles.add(this.roleService.findByName(name.trim()));
            }
        }
        return roles;
    }

    // Из-за того что у формы нет поля th:object в поле name поподают все строки для
    // th:fiel={*name}
    private String extractUserName(String names) {
        return names.split(",")[0];
    }
}
